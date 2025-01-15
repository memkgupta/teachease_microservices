package org.teachease.authservice.dtos;

import java.util.Optional;

public record UserDTO(String userId, String password, String email, Optional<String> name) {

}