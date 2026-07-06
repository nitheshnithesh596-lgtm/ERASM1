package com.erasm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.erasm.dto.LoginRequest;
import com.erasm.dto.LoginResponse;
import com.erasm.dto.RegisterRequest;
import com.erasm.dto.RegisterResponse;
import com.erasm.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomAccessDeniedHandler accessDeniedHandler;

    @MockBean
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Test
    void testRegister() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("nithesh");
        request.setEmail("nithesh@gmail.com");
        request.setPassword("password123");
        request.setRoleId(1L);

        RegisterResponse response = new RegisterResponse();
        response.setMessage("User registered successfully");

        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void testLogin() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("nithesh");
        request.setPassword("password123");

        LoginResponse response = new LoginResponse();
        response.setToken("jwt-token");
        response.setEmail("nithesh@gmail.com");
        response.setRole("ADMIN");

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.email").value("nithesh@gmail.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
}