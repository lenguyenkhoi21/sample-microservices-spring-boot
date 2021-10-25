package com.example.servicesb.service;

import com.example.servicesb.model.ProductMG;
import com.example.servicesb.repo.ProductRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductMGService {
    private final ProductRepo productRepo;


    public ProductMGService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Mono<ProductMG> findById(long id) {
        return productRepo.findByProductId(id);
    }
}
