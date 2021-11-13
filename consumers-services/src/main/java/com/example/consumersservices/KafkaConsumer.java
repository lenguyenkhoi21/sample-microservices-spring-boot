package com.example.consumersservices;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final String TOPIC = "Kafka_Example";

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
