package ru.zagrebin.processing.exchangeprocessing.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.zagrebin.processing.exchangeprocessing.model.AccountEvent;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class AccountEventSendingService {

    public static final String ACCOUNT_EVENTS = "account-events";
    private final ObjectMapper mapper = new ObjectMapper();
    private final KafkaTemplate<Long, String> kafkaTemplate;

    public AccountEventSendingService(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(AccountEvent evt) {
        var accountId = evt.getAccountId();
        String message;
        try {
            message = mapper.writeValueAsString(evt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var future = kafkaTemplate.send(ACCOUNT_EVENTS, accountId, message);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
