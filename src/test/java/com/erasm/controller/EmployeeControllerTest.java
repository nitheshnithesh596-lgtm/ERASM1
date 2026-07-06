package com.erasm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.erasm.dto.EmployeeDTO;
import com.erasm.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetEmployeeById() throws Exception {

        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Nithesh");
        dto.setLastName("Kumar");

        when(employeeService.getEmployeeById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Nithesh"))
                .andExpect(jsonPath("$.lastName").value("Kumar"));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }
    @Test
    void testCreateEmployee() throws Exception {

        EmployeeDTO input = new EmployeeDTO();
        input.setEmployeeCode("EMP001");
        input.setFirstName("Nithesh");
        input.setLastName("Kumar");
        input.setDesignation("Developer");
        input.setExperience(2.0);
        input.setUserId(1L);

        EmployeeDTO output = new EmployeeDTO();
        output.setEmployeeId(1L);
        output.setEmployeeCode("EMP001");
        output.setFirstName("Nithesh");
        output.setLastName("Kumar");
        output.setDesignation("Developer");
        output.setExperience(2.0);
        output.setUserId(1L);

        when(employeeService.saveEmployee(any(EmployeeDTO.class)))
                .thenReturn(output);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCode").value("EMP001"))
                .andExpect(jsonPath("$.firstName").value("Nithesh"));

        verify(employeeService, times(1))
                .saveEmployee(any(EmployeeDTO.class));
    }
    @Test
    void testDeleteEmployee() throws Exception {

        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1))
                .deleteEmployee(1L);
    }
}