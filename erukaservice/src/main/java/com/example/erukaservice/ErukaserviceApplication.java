package com.example.erukaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@RefreshScope
@EnableEurekaServer
@SpringBootApplication
public class ErukaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErukaserviceApplication.class, args);
    }

}
