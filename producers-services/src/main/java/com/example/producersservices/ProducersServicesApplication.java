package com.example.producersservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class ProducersServicesApplication {

    @Bean
    public Supplier<String> createMessage() {
        return () -> "From Producer Services " + ProcessHandle.current().pid();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducersServicesApplication.class, args);
    }

}
