package com.example.crud.application.dto;

import java.time.Instant;

import lombok.Value;

@Value
public class UserDTO {
    Long id;
    String name;
    String email;
    Instant createdAt;
}
