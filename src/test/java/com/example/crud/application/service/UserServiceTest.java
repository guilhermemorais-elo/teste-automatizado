package com.example.crud.application.service;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.crud.application.dto.UserCreateDTO;
import com.example.crud.application.dto.UserDTO;
import com.example.crud.application.dto.UserUpdateDTO;
import com.example.crud.application.exception.UserNotFoundException;
import com.example.crud.application.repository.UserRepository;
import com.example.crud.domain.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void testCreateUser() {
        UserCreateDTO dto = new UserCreateDTO("Test User", "test@example.com");
        User user = User.builder()
                .id(1L)
                .name(dto.getName())
                .email(dto.getEmail())
                .createdAt(Instant.now())
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(dto);

        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        UserUpdateDTO dto = new UserUpdateDTO("New Name", "new@example.com");
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
