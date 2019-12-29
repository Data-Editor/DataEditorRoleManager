package com.niek125.userconsumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.niek125.userconsumer.models.Action;
import com.niek125.userconsumer.models.KafkaHeader;
import com.niek125.userconsumer.models.KafkaMessage;
import com.niek125.userconsumer.models.User;
import com.niek125.userconsumer.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaUserConsumer.class);
    private final ObjectMapper objectMapper;
    private final UserRepo userRepo;

    @Autowired
    public KafkaUserConsumer(ObjectMapper objectMapper, UserRepo userRepo) {
        this.objectMapper = objectMapper;
        this.userRepo = userRepo;
    }

    @KafkaListener(topics = "user", groupId = "user-consumer")
    public void consume(String message) throws JsonProcessingException {
        logger.info("received message: " + message);
        logger.info("parsing user");
        final String[] pay = message.split("\n");
        final DocumentContext doc = JsonPath.parse(pay[0]);
        final KafkaMessage kafkaMessage = new KafkaMessage(new KafkaHeader(Action.valueOf(doc.read("$.action")), doc.read("$.payload")), pay[1]);
        final User user = objectMapper.readValue(kafkaMessage.getPayload(), User.class);
        switch (kafkaMessage.getKafkaHeader().getAction()) {
            case CREATE:
            case UPDATE:
                logger.info("saving user");
                userRepo.save(user);
                break;
            case DELETE:
                logger.info("deleting user");
                userRepo.deleteById(user.getUserid());
                break;
        }
        logger.info("successfully processed message");
    }
}
