package com.example.privateservicesa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@RefreshScope
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class PrivateServicesAApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateServicesAApplication.class, args);
    }

}
