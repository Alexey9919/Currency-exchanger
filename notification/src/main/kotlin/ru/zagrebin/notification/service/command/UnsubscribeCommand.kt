package ru.zagrebin.notification.service.command

import org.springframework.stereotype.Service
import ru.zagrebin.notification.service.SubscriptionService

@Service("unsubscribe")
class UnsubscribeCommand(val subscriptionService: SubscriptionService) : BotCommandProcessor {
    override fun processMessage(message: String, chatId: Long): String {
        subscriptionService.unsubscribeUser(chatId)
        return "Подписка отменена"
    }
}