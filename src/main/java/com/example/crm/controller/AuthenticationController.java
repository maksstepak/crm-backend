package com.example.crm.controller;

import com.example.crm.dto.UserDto;
import com.example.crm.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/me")
    ResponseEntity<UserDto> getCurrentUser() {
        var currentUser = authenticationService.getCurrentUser();
        var currentUserDto = new UserDto(currentUser);
        return ResponseEntity.ok(currentUserDto);
    }
}
