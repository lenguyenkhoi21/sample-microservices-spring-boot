package com.example.consumersservices;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaConsumer {
    private static final String TOPIC = "Kafka_Example";

    @KafkaListener(topics = TOPIC)
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }

    private String groupUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
