package com.example.servicesb.controller;

import com.example.servicesb.model.ProductMG;
import com.example.servicesb.service.ProductMGService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RestAPI {

    private final ProductMGService productMGService;

    public RestAPI(ProductMGService productMGService) {
        this.productMGService = productMGService;
    }

    @GetMapping(value = "/api/v1/product/{id}")
    public Mono<ProductMG> findById(@PathVariable long id) {
        return productMGService.findById(id);
    }
}
