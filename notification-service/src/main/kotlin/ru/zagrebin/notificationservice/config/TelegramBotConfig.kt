package ru.zagrebin.notificationservice.config

//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.ApplicationContext
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class TelegramBotConfig {
//
//    @Autowired
//    private lateinit var appContext: ApplicationContext
//
//    @Value("\${telegram.botName}")
//    private val botName: String = ""
//
//    @Value("\${telegram.token}")
//    private val token: String = ""
//
//    @Bean
//    fun botSettings(): BotSettings {
//        val resource = appContext.getResource("file:$token")
//        val content = if (resource.exists()) resource.file.readText()
//                else throw RuntimeException("File $token not found")
//        return BotSettings(botName, content)
//    }
//}