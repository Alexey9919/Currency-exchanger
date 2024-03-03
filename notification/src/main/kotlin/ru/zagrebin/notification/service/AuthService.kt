package ru.zagrebin.notification.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class AuthService(val resource: ResourceOwnerPasswordResourceDetails, val restTemplate: RestTemplate) {

    fun authUser(user: String, passwd: String): String {
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>().apply {
            add("username", user)
            add("password", passwd)
            add("grant_type", "password")
        }
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            setBasicAuth(resource.clientId, resource.clientSecret)
        }

        val entity = HttpEntity(formData, headers)
        val map = restTemplate.exchange(resource.accessTokenUri, HttpMethod.POST, entity, Map::class.java)
                .body as Map<String, *>
        return map["access_token"] as String
    }
}