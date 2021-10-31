package com.example.servicesa.controller;

import com.example.servicesa.model.Information;
import com.example.servicesa.model.Product;
import com.example.servicesa.services.Servicesb;
import com.example.servicesa.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPI {
    private static Logger logger = LoggerFactory.getLogger(RestAPI.class);

    private final Servicesb servicesb;

    public RestAPI(Servicesb servicesb) {
        this.servicesb = servicesb;
    }

    @GetMapping("/api/v1/infor")
    public Information getInformation() {
        logger.debug("/api/v1/infor Correlation id: {}", UserContext.getCorrelationId());
        return Information
                .builder()
                .app_info("services-a")
                .version_info("1.0")
                .build();
    }

    @GetMapping("/api/v1/product/{id}")
    public Product findProduct(@PathVariable long id) {
        return servicesb.getProduct(id);
    }
}
