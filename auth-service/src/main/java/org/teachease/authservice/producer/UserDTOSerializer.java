package org.teachease.authservice.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.teachease.authservice.dtos.UserDTO;

public class UserDTOSerializer implements Serializer<UserDTO>{
    @Override
    public byte[] serialize(String s, UserDTO userDTO) {
        byte[] retVal = null;
        UserEventDTO userEventDTO = new UserEventDTO(userDTO.userId(), userDTO.password(), userDTO.email(), userDTO.name().get());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(userEventDTO).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
