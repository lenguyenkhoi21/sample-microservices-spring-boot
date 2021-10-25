package com.example.userregistation;

import com.example.userregistation.config.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(ApiProperties.class)
public class UserRegistationApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRegistationApplication.class, args);
    }

}
