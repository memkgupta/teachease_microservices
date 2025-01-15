package org.teachease.authservice.producer;

public record UserEventDTO(String userId,String password,String email,String name) {
}
