package ru.zagrebin.processing.exchangeprocessing.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zagrebin.processing.exchangeprocessing.model.AccountEntity;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserId(Long userId);
}
