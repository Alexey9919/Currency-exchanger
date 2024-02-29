package ru.zagrebin.history.service

import org.springframework.stereotype.Service
import ru.zagrebin.history.model.AccountEvent
import ru.zagrebin.history.repository.AccountEventRepository

@Service
class AccountEventService (private val userService: ResolveUserService, private val repository: AccountEventRepository) {
    fun findAllAccountEvents(accountId: Long) : List<AccountEvent> {
        val userId = userService.resolveUserId()
        return repository.findAllByAccountIdAndUserIdOrderByCreatedDesc(accountId, userId)
    }
}