package org.teachease.authservice.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.teachease.authservice.dtos.UserDTO;

@Configuration
public class UserProducer {
    private final KafkaTemplate<String, UserDTO> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, UserDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(UserDTO userDTO) {
        System.out.println("Message sent: " + userDTO.userId());
        Message<UserDTO> message = MessageBuilder.withPayload(userDTO)
                .setHeader(KafkaHeaders.TOPIC,"auth-service").build();
        kafkaTemplate.send(message);
    }
}
