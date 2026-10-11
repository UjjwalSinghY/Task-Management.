package com.us.taskmanagement.service;

import com.us.taskmanagement.dto.AuthResponse;
import com.us.taskmanagement.dto.LoginRequest;
import com.us.taskmanagement.dto.RegisterRequest;
import com.us.taskmanagement.entity.AppUser;
import com.us.taskmanagement.security.JwtService;
import com.us.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setName("Ujjwal");
        registerRequest.setEmail("ujjwal@example.com");
        registerRequest.setPassword("StrongPass123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("ujjwal@example.com");
        loginRequest.setPassword("StrongPass123");
    }

    @Test
    void registerShouldSaveEncodedPassword() {
        when(userRepository.existsByEmail(
                "ujjwal@example.com")).thenReturn(false);

        when(passwordEncoder.encode("StrongPass123"))
                .thenReturn("encoded-password");

        String result = authService.register(registerRequest);

        assertEquals("Registration successful", result);

        verify(userRepository).save(argThat(user ->
                user.getName().equals("Ujjwal")
                        && user.getEmail().equals("ujjwal@example.com")
                        && user.getPassword().equals("encoded-password")
        ));
    }

    @Test
    void registerShouldRejectDuplicateEmail() {
        when(userRepository.existsByEmail(
                "ujjwal@example.com")).thenReturn(true);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.register(registerRequest)
        );

        assertEquals(HttpStatus.CONFLICT,
                exception.getStatusCode());

        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    void loginShouldReturnJwtForValidCredentials() {
        AppUser user = AppUser.builder()
                .id(1L)
                .name("Ujjwal")
                .email("ujjwal@example.com")
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(
                "ujjwal@example.com")).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "StrongPass123", "encoded-password"))
                .thenReturn(true);

        when(jwtService.generateToken("ujjwal@example.com"))
                .thenReturn("test-jwt-token");

        AuthResponse response = authService.login(loginRequest);

        assertEquals("test-jwt-token", response.getToken());
        assertEquals("Bearer", response.getTokenType());

        verify(jwtService).generateToken("ujjwal@example.com");
    }

    @Test
    void loginShouldRejectIncorrectPassword() {
        AppUser user = AppUser.builder()
                .id(1L)
                .name("Ujjwal")
                .email("ujjwal@example.com")
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(
                "ujjwal@example.com")).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "StrongPass123", "encoded-password"))
                .thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.login(loginRequest)
        );

        assertEquals(HttpStatus.UNAUTHORIZED,
                exception.getStatusCode());

        verify(jwtService, never()).generateToken(anyString());
    }
}

