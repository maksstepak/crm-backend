package com.example.crm.api;

import com.example.crm.entity.User;
import com.example.crm.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    public void shouldCreateUser() throws Exception {
        var body = new HashMap<>();
        body.put("email", "test1@test.com");
        body.put("password", "test123");
        body.put("firstName", "John");
        body.put("lastName", "Doe");

        this.mockMvc
                .perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());

        var optionalUser = userRepository.findByEmail("test1@test.com");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    @WithMockUser
    public void shouldGetUserById() throws Exception {
        var user = new User();
        user.setEmail("test1@test.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        this.mockMvc
                .perform(get("/api/users/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test1@test.com"));
    }

    @Test
    @WithMockUser
    public void shouldGetAllUsers() throws Exception {
        var user1 = new User();
        user1.setEmail("test1@test.com");
        user1.setPassword("password");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        var user2 = new User();
        user2.setEmail("test2@test.com");
        user2.setPassword("password");
        user2.setFirstName("John");
        user2.setLastName("Doe");
        var user3 = new User();
        user3.setEmail("test3@test.com");
        user3.setPassword("password");
        user3.setFirstName("John");
        user3.setLastName("Doe");
        userRepository.saveAll(List.of(user1, user2, user3));

        this.mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    @WithMockUser
    public void shouldUpdateUser() throws Exception {
        var user = new User();
        user.setEmail("test1@test.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);
        var body = new HashMap<>();
        body.put("firstName", "Adam");
        body.put("lastName", "Doe");

        this.mockMvc
                .perform(put("/api/users/{userId}", user.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());

        assertEquals("Adam", user.getFirstName());
    }

    @Test
    @WithMockUser
    public void shouldDeleteUser() throws Exception {
        var user = new User();
        user.setEmail("test1@test.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        this.mockMvc
                .perform(delete("/api/users/{userId}", user.getId()))
                .andExpect(status().isNoContent());

        var exists = userRepository.existsById(user.getId());
        assertFalse(exists);
    }
}
