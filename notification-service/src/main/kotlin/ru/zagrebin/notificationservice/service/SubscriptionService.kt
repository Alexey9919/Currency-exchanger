package ru.zagrebin.notificationservice.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class SubscriptionService {
    val subscriptions = ConcurrentHashMap<Long, Long>()

    fun subscribeUser(userId: Long, chatId: Long) = subscriptions.put(userId, chatId)

    fun unsubscribeUser(chatId: Long) = subscriptions.entries.removeIf { (_, value) -> value == chatId }

    fun getSubscription(userId: Long): Long? = subscriptions[userId]
}