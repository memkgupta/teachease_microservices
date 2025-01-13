package org.teachease.authservice.dtos;

public record LoginResponse(String access_token,String refresh_token) {
}