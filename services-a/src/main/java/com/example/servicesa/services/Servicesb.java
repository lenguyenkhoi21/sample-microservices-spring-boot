package com.example.servicesa.services;

import com.example.servicesa.model.Product;
import com.example.servicesa.utils.UserContext;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Servicesb {
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    public Product getProduct(long id) {
//        String url = "http://localhost:8082/api/v1/product/{id}";
//        List<ServiceInstance> instances = discoveryClient.getInstances("services-a");
//        instances.forEach(System.out::println);
//        if (instances.size() == 0) return null;
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Product> restExchange = restTemplate.exchange(url, HttpMethod.GET, null, Product.class, id);
//        return restExchange.getBody();
//    }
    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "services-b", fallbackMethod = "bullFallbackServiceB")
    @RateLimiter(name = "services-b", fallbackMethod = "bullFallbackServiceB")
    @Retry(name = "retryServices-b", fallbackMethod = "bullFallbackServiceB")
    @Bulkhead(name = "bulkheadServices-b", type= Bulkhead.Type.THREADPOOL, fallbackMethod = "bullFallbackServiceB")
    public Product getProduct(long id) {
        String correlation_id = UserContext.getCorrelationId();
        String url = "http://services-b/api/v1/product/{correlation-id}/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.add(UserContext.CORRELATION_ID, correlation_id);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Product> restExchange = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Product.class,
                correlation_id,
                id);


        return restExchange.getBody();
    }

    @SuppressWarnings("unsused")
    private Product bullFallbackServiceB(long id, IllegalArgumentException e) {
        return Product
                .builder()
                .id("none")
                .name("none")
                .description("none")
                .image("none")
                .price(0)
                .status(false)
                .productId(0)
                .build();
    }
}
