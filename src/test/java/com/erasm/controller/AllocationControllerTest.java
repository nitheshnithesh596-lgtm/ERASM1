package com.erasm.controller;

import com.erasm.dto.AllocationDTO;
import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;
import com.erasm.service.AllocationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AllocationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AllocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AllocationService allocationService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Test
    void testCreateAllocation() throws Exception {

        AllocationDTO dto = new AllocationDTO();
        dto.setAllocationId(1L);
        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setAllocationPercentage(50);
        dto.setAllocationDate(LocalDate.now());
        dto.setReleaseDate(LocalDate.now().plusDays(30));

        when(allocationService.saveAllocation(any(AllocationDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/allocations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allocationId").value(1))
                .andExpect(jsonPath("$.allocationPercentage").value(50));
    }

    @Test
    void testGetAllocationById() throws Exception {

        AllocationDTO dto = new AllocationDTO();
        dto.setAllocationId(1L);
        dto.setEmployeeId(1L);
        dto.setProjectId(1L);

        when(allocationService.getAllocationById(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/allocations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allocationId").value(1));
    }

    @Test
    void testGetAllAllocations() throws Exception {

        AllocationDTO dto = new AllocationDTO();
        dto.setAllocationId(1L);

        when(allocationService.getAllAllocations())
                .thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/allocations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].allocationId").value(1));
    }

    @Test
    void testUpdateAllocation() throws Exception {

        AllocationDTO dto = new AllocationDTO();
        dto.setAllocationId(1L);
        dto.setAllocationPercentage(75);

        when(allocationService.updateAllocation(any(Long.class), any(AllocationDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/allocations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allocationPercentage").value(75));
    }

    @Test
    void testDeleteAllocation() throws Exception {

        doNothing().when(allocationService).deleteAllocation(1L);

        mockMvc.perform(delete("/api/allocations/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Allocation deleted successfully"));
    }
}