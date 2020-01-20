package com.niek125.userconsumer.kafka;

import com.niek125.userconsumer.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaUserConsumer.class);
    private final EventHandler eventHandler;

    @Autowired
    public KafkaUserConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "user", groupId = "user-consumer")
    public void consume(String message) {
        logger.info("received message: {}", message);
        eventHandler.processMessage(message);
        logger.info("successfully processed message");
    }
}
