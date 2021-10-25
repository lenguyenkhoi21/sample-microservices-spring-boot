package com.example.userregistation.services;

import com.example.userregistation.config.ApiProperties;
import com.example.userregistation.model.Token;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TokenService {
    private final ApiProperties apiProperties;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Getter
    private Token token;

    public TokenService(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    private Token fetchTokenFetch() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", apiProperties.getKeycloak().getGrant_type());
        requestBody.add("username", apiProperties.getKeycloak().getAdmin());
        requestBody.add("password", apiProperties.getKeycloak().getPassword());
        requestBody.add("client_id", apiProperties.getKeycloak().getClientID());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        String url = apiProperties.getKeycloak().getDomain() + "/auth/realms/myrealm/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Token> restExchange = restTemplate.exchange(url, HttpMethod.POST, request, Token.class);
        return restExchange.getBody();
    }

    @Scheduled(fixedDelay = 270*1000)
    private void scheduleFetchToken() {
        logger.info("Fetch token from keycloak :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        this.token = this.fetchTokenFetch();
    }
}
