package com.example.crud.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserCreateDTO {
    @NotBlank
    @Size(min = 2, max = 64)
    String name;

    @NotBlank
    @Email
    String email;
}
