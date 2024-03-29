package ru.zagrebin.currency.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.zagrebin.currency.client.HttpCurrencyDateRateClient;
import ru.zagrebin.currency.schema.ValCurs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toMap;

@Service
public class CbrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CbrService.class);

    private final Cache<LocalDate, Map<String, BigDecimal>> cache;

    private HttpCurrencyDateRateClient client;

    private final Timer timer;

    public CbrService(HttpCurrencyDateRateClient client, MeterRegistry meterRegistry) {
        this.timer = Timer.builder("cbr.request").description("CBR service request").publishPercentiles(1.0, 0.99).register(meterRegistry);
        this.cache = CacheBuilder.newBuilder().build();
        this.client = client;
    }

    public BigDecimal requestByCurrencyCode(String code) {
        LOGGER.info("Request service by currency code {}", code);
        return timer.record(() -> {
            try {
                return cache.get(LocalDate.now(), this::callAllByCurrentDate).get(code);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        );
    }

    private Map<String, BigDecimal> callAllByCurrentDate() {
        var xml = client.requestByDate(LocalDate.now());
        ValCurs response = unmarshall(xml);
        return response.getValute().stream().collect(toMap(ValCurs.Valute::getCharCode, item -> parseWithLocale(item.getValue())));
    }

    private BigDecimal parseWithLocale(String currency) {
        try {
            double v = NumberFormat.getNumberInstance(Locale.getDefault()).parse(currency).doubleValue();
            return BigDecimal.valueOf(v);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private ValCurs unmarshall(String xml) {
        try (StringReader reader = new StringReader(xml)) {
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            return (ValCurs) context.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


}
