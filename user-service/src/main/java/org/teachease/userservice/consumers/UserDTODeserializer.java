package org.teachease.userservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class UserDTODeserializer implements Deserializer<UserDTO> {
    @Override
    public UserDTO deserialize(String s, byte[] bytes) {
      UserDTO userDTO = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            userDTO = mapper.readValue(bytes, UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }


}
