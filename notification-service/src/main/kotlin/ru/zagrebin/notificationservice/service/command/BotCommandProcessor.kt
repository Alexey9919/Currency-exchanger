package ru.zagrebin.notificationservice.service.command

interface BotCommandProcessor {
    fun processMessage(message: String, chatId: Long): String
}