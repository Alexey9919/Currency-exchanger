package ru.zagrebin.notification.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import ru.zagrebin.notification.model.AccountEvent
import ru.zagrebin.notification.model.Operation


@Service
class AccountEventKafkaListener(val subscription: SubscriptionService, val telegramAgent: TelegramSubscriptionServiceAgent) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val mapper = jacksonObjectMapper()

    @KafkaListener(topics=["account-events"])
    fun consumerEvent(record: ConsumerRecord<Long, String>) {
        val key = record.key()
        val value = record.value()

        logger.info("Consume message $value for account: $key")

        val evt: AccountEvent = try {
            mapper.readValue(value)
        } catch (e: Exception) {
            logger.error(e.message, e)
            throw e
        }

        val chatId = subscription.getSubscription(evt.userId)
        if (chatId != null) {
            val message = when (evt.operation) {
                Operation.PUT -> formatPutEvt(evt)
                Operation.EXCHANGE -> formatExchangeEvt(evt)
            }
            telegramAgent.sendNotification(chatId, message)
        }

        logger.info("Consumed event $evt")
    }

    private fun formatPutEvt(evt: AccountEvent): String =
            "Счет номер ${evt.accountId}. Дата: ${evt.created}. \n" +
                    "Операция ${evt.operation} на сумму ${evt.amount} ${evt.currency}"

    private fun formatExchangeEvt(evt: AccountEvent): String =
            "Счет номер ${evt.accountId}. Дата: ${evt.created}. \n" +
                    "Операция ${evt.operation} на сумму ${evt.amount} ${evt.currency} со счета номер ${evt.fromAccount}"
}