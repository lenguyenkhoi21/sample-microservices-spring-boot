package com.example.servicesa.controller;

import com.example.servicesa.model.Product;
import com.example.servicesa.services.Servicesb;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPI {
    private final Servicesb servicesb;

    public RestAPI(Servicesb servicesb) {
        this.servicesb = servicesb;
    }

    @GetMapping("/api/v1/product/{id}")
    public Product findProduct(@PathVariable long id) {
        return servicesb.getProduct(id);
    }
}
