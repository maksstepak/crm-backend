package com.example.crm.dto;

import com.example.crm.entity.User;

public record UserDto(Long id, String email, String firstName, String lastName) {
    public UserDto(User user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }
}
