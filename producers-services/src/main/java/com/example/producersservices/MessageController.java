package com.example.producersservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/api/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendMessage() {
        kafkaTemplate.send(TOPIC, "Hello From Producer Services "
                + ProcessHandle.current().pid() + " in thread "
                + Thread.currentThread().getName());
        discoveryClient.getInstances("CONSUMERS-SERVICES").forEach(serviceInstance -> {
            System.out.println(serviceInstance.getInstanceId() + " " + serviceInstance.getHost() + " " + serviceInstance.getPort());
        });
        return "{ \" Message \" : \" OK \"  }";
    }

    private static final String TOPIC = "Kafka_Example";
}
