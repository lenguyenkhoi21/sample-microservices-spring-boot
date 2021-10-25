package com.example.servicesb.repo;

import com.example.servicesb.model.ProductMG;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductRepo extends ReactiveMongoRepository<ProductMG, String> {
    Mono<ProductMG> findByProductId(long productId);
}
