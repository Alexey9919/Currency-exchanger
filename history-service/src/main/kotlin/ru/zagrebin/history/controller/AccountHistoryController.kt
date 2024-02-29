package ru.zagrebin.history.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.zagrebin.history.model.AccountEvent
import ru.zagrebin.history.service.AccountEventService

@RestController
@RequestMapping("history")
class AccountHistoryController(private val historyService: AccountEventService) {
    @GetMapping("/account/{id}")
    fun retrieveAccountHistory(@PathVariable("id") accountId: Long) : List<AccountEvent> =
        historyService.findAllAccountEvents(accountId)
}