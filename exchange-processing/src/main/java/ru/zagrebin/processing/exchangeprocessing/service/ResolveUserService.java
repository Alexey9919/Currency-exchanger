package ru.zagrebin.processing.exchangeprocessing.service;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.sleuth.instrument.web.mvc.TracingClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResolveUserService {
    private final UserInfoRestTemplateFactory restTemplateFactory;
    private final ResourceServerProperties resource;
    private final TracingClientHttpRequestInterceptor interceptor;


    public ResolveUserService(UserInfoRestTemplateFactory restTemplateFactory, ResourceServerProperties resource, TracingClientHttpRequestInterceptor interceptor) {
        this.restTemplateFactory = restTemplateFactory;
        this.resource = resource;
        this.interceptor = interceptor;
    }

    public Long resolveUserId() {
        OAuth2RestTemplate userInfoRestTemplate = restTemplateFactory.getUserInfoRestTemplate();

        userInfoRestTemplate.setInterceptors(List.of(interceptor));

        Map<String, Object> response = userInfoRestTemplate.getForEntity(resource.getUserInfoUri(), Map.class).getBody();
        return Optional.ofNullable(response)
                .map(r -> r.get("principal"))
                .map(Map.class::cast)
                .map(v -> (Integer) v.get("userId"))
                .map(Long::valueOf)
                .orElse(null);
    }
}
