package ru.zagrebin.processing.exchangeprocessing.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.zagrebin.processing.exchangeprocessing.model.AccountEvent;

@Service
public class AccountOperationEventListener {

    private final AccountEventSendingService sendingService;

    public AccountOperationEventListener(AccountEventSendingService sendingService) {
        this.sendingService = sendingService;
    }

    @TransactionalEventListener
    public void handleEvent(AccountEvent evt){
        sendingService.sendEvent(evt);
    }
}
