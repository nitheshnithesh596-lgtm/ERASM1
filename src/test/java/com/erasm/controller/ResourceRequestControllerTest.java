package com.erasm.controller;

import com.erasm.dto.ResourceRequestDTO;
import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;
import com.erasm.service.ResourceRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
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

@WebMvcTest(ResourceRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
class ResourceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResourceRequestService resourceRequestService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Test
    void testCreateResourceRequest() throws Exception {

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setRequestId(1L);
        dto.setRequiredSkill("Java");
        dto.setRequiredCount(3);

        when(resourceRequestService.saveResourceRequest(any(ResourceRequestDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/resource-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requiredSkill").value("Java"));
    }

    @Test
    void testGetById() throws Exception {

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setRequestId(1L);
        dto.setRequiredSkill("Spring Boot");

        when(resourceRequestService.getResourceRequestById(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/resource-requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requiredSkill").value("Spring Boot"));
    }

    @Test
    void testGetAll() throws Exception {

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setRequestId(1L);
        dto.setRequiredSkill("React");

        when(resourceRequestService.getAllResourceRequests())
                .thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/resource-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requiredSkill").value("React"));
    }

    @Test
    void testApproveRequest() throws Exception {

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setRequestId(1L);
        dto.setStatus("APPROVED");

        when(resourceRequestService.approveRequest(1L))
                .thenReturn(dto);

        mockMvc.perform(put("/resource-requests/1/approve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void testRejectRequest() throws Exception {

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setRequestId(1L);
        dto.setStatus("REJECTED");

        when(resourceRequestService.rejectRequest(1L))
                .thenReturn(dto);

        mockMvc.perform(put("/resource-requests/1/reject"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }

    @Test
    void testDeleteRequest() throws Exception {

        org.mockito.Mockito.doNothing()
                .when(resourceRequestService)
                .deleteResourceRequest(1L);

        mockMvc.perform(delete("/resource-requests/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));
    }
}