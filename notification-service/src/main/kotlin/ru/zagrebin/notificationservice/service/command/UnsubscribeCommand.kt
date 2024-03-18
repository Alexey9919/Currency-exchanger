package ru.zagrebin.notificationservice.service.command

import org.springframework.stereotype.Service
import ru.zagrebin.notificationservice.service.SubscriptionService

@Service("unsubscribe")
class UnsubscribeCommand(val subscriptionService: SubscriptionService) : BotCommandProcessor {
    override fun processMessage(message: String, chatId: Long): String {
        subscriptionService.unsubscribeUser(chatId)
        return "Подписка отменена"
    }
}