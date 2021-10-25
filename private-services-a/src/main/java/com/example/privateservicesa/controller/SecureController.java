package com.example.privateservicesa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class SecureController {

    @RolesAllowed("USER")
    @GetMapping("/api/v1/private-services-a/secured-message")
    public String getSecureMessage() {
        return "{ \"Message\" : \"Secure-Message\"  }";
    }
}
