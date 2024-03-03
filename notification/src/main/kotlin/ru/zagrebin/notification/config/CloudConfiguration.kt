package ru.zagrebin.notification.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.web.client.RestTemplate

@Configuration
@EnableOAuth2Client
class CloudConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    fun resourceDetails(): ResourceOwnerPasswordResourceDetails {
        return ResourceOwnerPasswordResourceDetails()
    }

    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder.build()
    }
}