package com.erasm.controller;

import com.erasm.dto.ProjectDTO;
import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;
import com.erasm.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Test
    void testCreateProject() throws Exception {

        ProjectDTO dto = new ProjectDTO();
        dto.setProjectId(1L);
        dto.setProjectName("ERASM");
        dto.setClientName("ABC Client");
        dto.setStartDate(java.time.LocalDate.now());
        dto.setEndDate(java.time.LocalDate.now().plusDays(10));
        dto.setTechnologyStack("Java, Spring Boot");
        dto.setBudget(100000.0);
        dto.setStatus("ACTIVE");

        when(projectService.saveProject(any(ProjectDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("ERASM"))
                .andExpect(jsonPath("$.clientName").value("ABC Client"));
    }

    @Test
    void testGetProjectById() throws Exception {

        ProjectDTO dto = new ProjectDTO();
        dto.setProjectId(1L);
        dto.setProjectName("ERASM");

        when(projectService.getProjectById(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.projectName").value("ERASM"));
    }

    @Test
    void testDeleteProject() throws Exception {

        doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Project deleted successfully"));
    }
}