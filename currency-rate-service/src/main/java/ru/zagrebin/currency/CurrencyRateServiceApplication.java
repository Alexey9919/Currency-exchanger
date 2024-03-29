package ru.zagrebin.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import ru.zagrebin.currency.config.CurrencyClientCfg;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyClientCfg.class)
@EnableDiscoveryClient
public class CurrencyRateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateServiceApplication.class, args);
    }

}
