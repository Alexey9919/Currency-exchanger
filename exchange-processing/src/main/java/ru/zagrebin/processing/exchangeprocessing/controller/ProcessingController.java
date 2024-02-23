package ru.zagrebin.processing.exchangeprocessing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zagrebin.processing.exchangeprocessing.dto.ExchangeMoneyDTO;
import ru.zagrebin.processing.exchangeprocessing.dto.NewAccountDTO;
import ru.zagrebin.processing.exchangeprocessing.dto.PutAccountMoneyDTO;
import ru.zagrebin.processing.exchangeprocessing.model.AccountEntity;
import ru.zagrebin.processing.exchangeprocessing.service.AccountService;
import ru.zagrebin.processing.exchangeprocessing.service.ExchangeService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("processing")
@RequiredArgsConstructor
public class ProcessingController {
    private final AccountService accountService;

    private final ExchangeService exchangeService;


    @PostMapping("/account")
    public AccountEntity createAccount(@RequestBody NewAccountDTO account) {
        return accountService.createNewAccount(account);
    }

    @PutMapping("/account/{id}")
    public AccountEntity putMoney(@PathVariable("id") Long accountId, @RequestBody PutAccountMoneyDTO data) {
        return accountService.addMoneyToAccount(data.getUid(), accountId, data.getMoney());
    }

    @PutMapping("/exchange/{uid}")
    public BigDecimal exchange(@PathVariable("uid") String uid, @RequestBody ExchangeMoneyDTO data) {
        return exchangeService.exchangeCurrency(uid, data.getFromAccountId(), data.getToAccountId(), data.getMoney());
    }

    @GetMapping("/accounts")
    public List<AccountEntity> getAllAccount() {
        return accountService.getAllAccount();
    }
}
