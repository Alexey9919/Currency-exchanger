package ru.zagrebin.notification.service.command

import org.springframework.stereotype.Service
import ru.zagrebin.notification.service.AuthService
import ru.zagrebin.notification.service.ResolveUserService
import ru.zagrebin.notification.service.SubscriptionService

@Service("subscribe")
class SubscribeCommand(
        val authService: AuthService,
        val resolveUserService: ResolveUserService,
        val subscriptionService: SubscriptionService
) : BotCommandProcessor {
    override fun processMessage(message: String, chatId: Long): String {
        val (user, password) = message.split(":").let { array ->
            array[0] to array[1]
        }

        val token = authService.authUser(user, password)
        val userId = resolveUserService.resolveUserId(token)

        if (userId != null) {
            subscriptionService.subscribeUser(userId, chatId)
            return "Подписка оформлена"
        } else {
            return "Подписка не оформлена, пользователь $user не найден"
        }

    }
}