package com.niek125.roleconsumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.niek125.roleconsumer.models.Action;
import com.niek125.roleconsumer.models.KafkaHeader;
import com.niek125.roleconsumer.models.KafkaMessage;
import com.niek125.roleconsumer.models.Role;
import com.niek125.roleconsumer.repository.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaRoleConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaRoleConsumer.class);
    private final ObjectMapper objectMapper;
    private final RoleRepo messageRepo;

    @Autowired
    public KafkaRoleConsumer(ObjectMapper objectMapper, RoleRepo messageRepo) {
        this.objectMapper = objectMapper;
        this.messageRepo = messageRepo;
    }

    @KafkaListener(topics = "role", groupId = "role-consumer")
    public void consume(String message) throws JsonProcessingException {
        logger.info("received message: " + message);
        logger.info("parsing role");
        final String[] pay = message.split("\n");
        final DocumentContext doc = JsonPath.parse(pay[0]);
        final KafkaMessage kafkaMessage = new KafkaMessage(new KafkaHeader(Action.valueOf(doc.read("$.action")), doc.read("$.payload")), pay[1]);
        final Role role = objectMapper.readValue(kafkaMessage.getPayload(), Role.class);
        switch (kafkaMessage.getKafkaHeader().getAction()) {
            case CREATE:
            case UPDATE:
                logger.info("saving role");
                messageRepo.save(role);
                break;
            case DELETE:
                logger.info("deleting role");
                messageRepo.deleteById(role.getRoleid());
                break;
        }
        logger.info("successfully processed message");
    }
}
