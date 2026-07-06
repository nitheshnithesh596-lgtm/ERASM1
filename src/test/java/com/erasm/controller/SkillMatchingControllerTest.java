package com.erasm.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.erasm.dto.EmployeeSkillMatchDTO;
import com.erasm.repository.UserRepository;
import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;
import com.erasm.security.JwtService;
import com.erasm.service.SkillMatchingService;

@WebMvcTest(SkillMatchingController.class)
@AutoConfigureMockMvc(addFilters = false)
class SkillMatchingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillMatchingService skillMatchingService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    void testGetEmployeesBySkill() throws Exception {

        EmployeeSkillMatchDTO dto = new EmployeeSkillMatchDTO();

        dto.setEmployeeId(1L);
        dto.setEmployeeName("John Doe");
        dto.setSkillName("Java");
        dto.setSkillLevel("Advanced");
        dto.setExperienceYears(5);
        dto.setUtilization(100);

        when(skillMatchingService.getEmployeesBySkill("Java"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/skill-matching/Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1))
                .andExpect(jsonPath("$[0].employeeName").value("John Doe"))
                .andExpect(jsonPath("$[0].skillName").value("Java"))
                .andExpect(jsonPath("$[0].skillLevel").value("Advanced"))
                .andExpect(jsonPath("$[0].experienceYears").value(5))
                .andExpect(jsonPath("$[0].utilization").value(100));
    }
}