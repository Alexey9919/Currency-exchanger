package ru.zagrebin.processing.exchangeprocessing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zagrebin.processing.exchangeprocessing.dto.NewAccountDTO;
import ru.zagrebin.processing.exchangeprocessing.model.AccountEntity;
import ru.zagrebin.processing.exchangeprocessing.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountService {

    public final AccountRepository accountRepository;


    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AccountEntity createNewAccount(NewAccountDTO dto) {

        var account = new AccountEntity();
        account.setCurrencyCode(dto.getCurrencyCode());
        account.setUserId(dto.getUserId());
        account.setBalance(new BigDecimal(0));

        var created = accountRepository.save(account);
        return created;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountEntity addMoneyToAccount(String uid, Long accountId, BigDecimal money) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        var result = account.map( acc -> {
            var balance = acc.getBalance().add(money);
            acc.setBalance(balance);

            return accountRepository.save(acc);
        }).orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
        return result;
    }

    public AccountEntity getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account with ID " + id + " not found"));
    }

    public List<AccountEntity> getAllAccount(Long userId) {
        return accountRepository.findByUserId(userId);
    }
}
