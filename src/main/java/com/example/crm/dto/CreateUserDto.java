package com.example.crm.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String email, @NotBlank String password, @NotBlank String firstName,
                            @NotBlank String lastName) {
}
