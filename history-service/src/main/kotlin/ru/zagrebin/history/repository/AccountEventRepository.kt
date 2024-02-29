package ru.zagrebin.history.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.zagrebin.history.model.AccountEvent
import ru.zagrebin.history.model.EventKey

@Repository
interface AccountEventRepository: JpaRepository<AccountEvent, EventKey> {
    fun findAllByAccountIdAndUserIdOrderByCreatedDesc(accountId: Long, userId: Long?): List<AccountEvent>
}