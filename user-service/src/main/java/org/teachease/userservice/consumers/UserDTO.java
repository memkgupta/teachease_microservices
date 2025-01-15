package org.teachease.userservice.consumers;

import java.util.Optional;

public record UserDTO(String userId, String password, String email, String name) {

}