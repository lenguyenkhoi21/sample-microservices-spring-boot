package com.example.userregistation.controller;

import com.example.userregistation.config.ApiProperties;
import com.example.userregistation.model.Token;
import com.example.userregistation.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final ApiProperties apiProperties;

    private final TokenService tokenService;

    public TestController(ApiProperties apiProperties, TokenService tokenService) {
        this.apiProperties = apiProperties;
        this.tokenService = tokenService;
    }

    @GetMapping("/keycloak")
    public ApiProperties.Keycloak keycloak() {
        return apiProperties.getKeycloak();
    }

    @GetMapping("/token")
    public Token getToken() {
        return tokenService.getToken();
    }
}
