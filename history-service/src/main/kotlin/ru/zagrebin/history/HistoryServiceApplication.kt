package ru.zagrebin.history

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HistoryServiceApplication

fun main(args: Array<String>) {
	runApplication<HistoryServiceApplication>(*args)
}
