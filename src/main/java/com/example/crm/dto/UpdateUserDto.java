package com.example.crm.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(@NotBlank String firstName, @NotBlank String lastName) {
}
