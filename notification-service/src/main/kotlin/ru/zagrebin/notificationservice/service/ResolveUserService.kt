package ru.zagrebin.notificationservice.service

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.stereotype.Service

@Service
class ResolveUserService(val details: ResourceOwnerPasswordResourceDetails, val resource: ResourceServerProperties) {
    fun resolveUserId(token: String): Long? {
        val accessToken = DefaultOAuth2AccessToken(token)
        val oauthCtx: OAuth2ClientContext = DefaultOAuth2ClientContext(accessToken)

        val restTemplate = OAuth2RestTemplate(details, oauthCtx)
        val response = restTemplate.getForEntity("http://172.17.0.1:8080/user", Map::class.java).body

        return response?.let {r -> r["principal"] as MutableMap<*, *>}
                .let { v -> v?.get("userId") as Int?}
                .let { u -> u?.toLong()}
    }
}