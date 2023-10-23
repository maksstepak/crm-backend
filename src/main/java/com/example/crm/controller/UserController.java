package com.example.crm.controller;

import com.example.crm.dto.CreateUserDto;
import com.example.crm.dto.UpdateUserDto;
import com.example.crm.dto.UserDto;
import com.example.crm.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody CreateUserDto request) {
        userService.create(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getById(@PathVariable Long userId) {
        var userDto = userService.getById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    ResponseEntity<Page<UserDto>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = userService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{userId}")
    ResponseEntity<Void> update(@PathVariable Long userId, @Valid @RequestBody UpdateUserDto request) {
        userService.update(userId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
