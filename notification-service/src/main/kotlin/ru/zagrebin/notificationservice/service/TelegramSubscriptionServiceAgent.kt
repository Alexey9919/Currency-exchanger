package ru.zagrebin.notificationservice.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.zagrebin.notificationservice.service.command.BotCommandProcessor

@Service
class TelegramSubscriptionServiceAgent(
    val commands: Map<String, BotCommandProcessor>
) : TelegramLongPollingBot() {
    override fun getBotToken(): String = "6787552943:AAHR9dG3-_sxGhUtEJqWzHWTpa7DMOJ92kM"

    override fun getBotUsername(): String = "java-micro-notification"

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId

            val responseText : String = if (message.hasText()) {
                val messageText = message.text
                resolveCommand(messageText).let {command ->
                    if (command != null && command in commands)
                        commands[command]?.processMessage(extractMessage(messageText, command), chatId)
                                ?: "Команда $command не зарегистрирована"
                    else
                        "Команда из $messageText не распознана"
                }
            } else {
                "Введите текстовую команду"
            }
            sendNotification(chatId, responseText)
        }
    }

    private fun extractMessage(messageText: String, command: String): String =
        messageText.substring(command.length+1).trim()


    private fun resolveCommand(text: String): String? =
            if (text.startsWith("/")) text.substringBefore(' ').substring(1) else null

    fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        execute(responseMessage)
    }

}