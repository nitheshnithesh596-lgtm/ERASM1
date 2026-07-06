package com.erasm.controller;

import com.erasm.dto.UserDTO;
import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;
import com.erasm.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Test
    void testSaveUser() throws Exception {

        UserDTO dto = new UserDTO();
        dto.setUserId(1L);
        dto.setUsername("Nithesh");
        dto.setEmail("nithesh@gmail.com");

        when(userService.saveUser(any(UserDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("Nithesh"));
    }

    @Test
    void testGetAllUsers() throws Exception {

        UserDTO dto = new UserDTO();
        dto.setUserId(1L);
        dto.setUsername("Nithesh");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("Nithesh"));
    }

    @Test
    void testGetUserById() throws Exception {

        UserDTO dto = new UserDTO();
        dto.setUserId(1L);
        dto.setUsername("Nithesh");

        when(userService.getUserById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("Nithesh"));
    }

    @Test
    void testUpdateUser() throws Exception {

        UserDTO dto = new UserDTO();
        dto.setUserId(1L);
        dto.setUsername("Updated Name");

        when(userService.updateUser(eq(1L), any(UserDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Updated Name"));
    }

    @Test
    void testDeleteUser() throws Exception {

        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));
    }
}