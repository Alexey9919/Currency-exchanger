package ru.zagrebin.notification.service.command

interface BotCommandProcessor {
    fun processMessage(message: String, chatId: Long): String
}