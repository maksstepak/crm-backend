package com.example.crm.service;

import com.example.crm.dto.CreateUserDto;
import com.example.crm.dto.UpdateUserDto;
import com.example.crm.dto.UserDto;
import com.example.crm.entity.User;
import com.example.crm.exception.ResourceNotFoundException;
import com.example.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(CreateUserDto dto) {
        var user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        userRepository.save(user);
    }

    public UserDto getById(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        var userDto = new UserDto(user);
        return userDto;
    }

    public Page<UserDto> getAll(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        var userDtos = users.map(UserDto::new);
        return userDtos;
    }

    @Transactional
    public void update(Long userId, UpdateUserDto dto) {
        var user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
    }

    public void delete(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        userRepository.delete(user);
    }
}
