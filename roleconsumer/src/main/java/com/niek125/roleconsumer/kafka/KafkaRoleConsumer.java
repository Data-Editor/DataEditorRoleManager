package com.niek125.roleconsumer.kafka;

import com.niek125.roleconsumer.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaRoleConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaRoleConsumer.class);
    private final EventHandler eventHandler;

    @Autowired
    public KafkaRoleConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = {"role", "project", "user"}, groupId = "role-consumer")
    public void consume(String message) {
        logger.info("received message: {}", message);
        eventHandler.processMessage(message);
        logger.info("successfully processed message");
    }
}
