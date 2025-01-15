package org.teachease.userservice.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.teachease.userservice.dtos.UserInfoDTO;
import org.teachease.userservice.services.UserService;

@Service
public class UserConsumer {
    private final UserService userService;

    public UserConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "auth-service",groupId = "my-group")
    public void listen(UserDTO userDTO) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(userDTO.userId());
        userInfoDTO.setEmail(userDTO.email());
        userInfoDTO.setName(userDTO.name());
       userService.createOrUpdateUser(userInfoDTO);
    }
}
