package com.example.userregistation.controller;

import com.example.userregistation.DTO.UserDTO;
import com.example.userregistation.DTO.UserPassDTO;
import com.example.userregistation.config.ApiProperties;
import com.example.userregistation.model.Token;
import com.example.userregistation.payload.UseRegistration;
import com.example.userregistation.payload.UserPasswordReset;
import com.example.userregistation.response.UserInforResponse;
import com.example.userregistation.services.TokenService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserRegistrationController {

    private final ApiProperties apiProperties;
    private final TokenService tokenService;

    public UserRegistrationController(ApiProperties apiProperties, TokenService tokenService) {
        this.apiProperties = apiProperties;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/v1/register")
    public String register(@RequestBody UserDTO userDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Bearer " + tokenService.getToken().getAccess_token());

        UseRegistration payload = UseRegistration
                .builder()
                .username(userDTO.getUsername())
                .build();

        HttpEntity<UseRegistration> request = new HttpEntity<>(payload, headers);

        String URL_CREATE_USER = apiProperties.getKeycloak().getDomain() + "/auth/admin/realms/"
                + apiProperties.getKeycloak().getRealm() + "/users";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Void> response = restTemplate.exchange(
                URL_CREATE_USER,
                HttpMethod.POST,
                request,
                Void.class);

        String SUCCESS = "{ \"Message\" : \"Success\"  }";
        String FAILED = "{ \"Message\" : \"Failed\"  }";
        return response.getStatusCode() == HttpStatus.CREATED ? SUCCESS : FAILED ;
    }

    @PostMapping("/api/v1/reset-password")
    public String resetPassword(@RequestBody UserPassDTO userPassDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Bearer " + tokenService.getToken().getAccess_token());

        UserPasswordReset payload = UserPasswordReset
                .builder()
                .value(userPassDTO.getPassword())
                .build();
        HttpEntity<UserPasswordReset> entity = new HttpEntity<>(payload, headers);

        String userId = getUserId(userPassDTO.getUsername());

        String URL_RESET_PASSWORD = apiProperties.getKeycloak().getDomain()
                + "/auth/admin/realms/myrealm/users/" + userId + "/reset-password";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                URL_RESET_PASSWORD,
                HttpMethod.PUT,
                entity,
                Void.class);
        String SUCCESS = "{ \"Message\" : \"Success\"  }";
        String FAILED = "{ \"Message\" : \"Failed\"  }";
        return response.getStatusCode() == HttpStatus.NO_CONTENT ? SUCCESS : FAILED;
    }

    @PostMapping("/api/v1/login")
    public Token login(@RequestBody UserPassDTO userPassDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", apiProperties.getKeycloak().getGrant_type());
        requestBody.add("username", userPassDTO.getUsername());
        requestBody.add("password", userPassDTO.getPassword());
        requestBody.add("client_id", apiProperties.getKeycloak().getClientID());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        String URL_GET_ACCESS_TOKEN = apiProperties.getKeycloak().getDomain() + "/auth/realms/myrealm/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Token> response = restTemplate.exchange(
                URL_GET_ACCESS_TOKEN,
                HttpMethod.POST,
                request,
                Token.class );

        return response.getBody();
    }

    private String getUserId(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Bearer " + tokenService.getToken().getAccess_token());

        HttpEntity<String> request = new HttpEntity<>(headers);
        String URL_LIST_USER_ID = apiProperties.getKeycloak().getDomain() + "/auth/admin/realms/"
                + apiProperties.getKeycloak().getRealm()
                + "/users?username=" + username + "&exact=true";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<UserInforResponse>> response = restTemplate.exchange(
                URL_LIST_USER_ID,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>(){});
        List<UserInforResponse> users = response.getBody();

        String userId = "";

        if (users.get(0) != null) {
            userId = users.get(0).getId();
        }

        return userId;
    }

}
