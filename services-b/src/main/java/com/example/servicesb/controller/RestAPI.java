package com.example.servicesb.controller;

import com.example.servicesb.model.ProductMG;
import com.example.servicesb.service.ProductMGService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RestAPI {
    private static final Logger logger = LoggerFactory.getLogger(RestAPI.class);

    private final ProductMGService productMGService;

    @GetMapping(value = "/api/v1/product/{correlation-id}/{id}")
    public Mono<ProductMG> findById(@PathVariable("correlation-id") String correlation_id, @PathVariable("id") long id ) {
        logger.debug("/api/v1/product/:correlation-id/:id for correlation_id: {}", correlation_id);
        return productMGService.findById(id);
    }
}
