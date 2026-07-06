package com.erasm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.erasm.dto.SkillDTO;
import com.erasm.security.CustomAccessDeniedHandler;
import com.erasm.security.CustomAuthenticationEntryPoint;
import com.erasm.security.JwtAuthenticationFilter;
import com.erasm.security.JwtService;
import com.erasm.repository.UserRepository;
import com.erasm.service.SkillService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SkillController.class)
@AutoConfigureMockMvc(addFilters = false)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SkillService skillService;

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
    void testCreateSkill() throws Exception {

        SkillDTO dto = new SkillDTO();
        dto.setSkillId(1L);
        dto.setSkillName("Java");

        when(skillService.saveSkill(any(SkillDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillId").value(1))
                .andExpect(jsonPath("$.skillName").value("Java"));
    }

    @Test
    void testGetSkillById() throws Exception {

        SkillDTO dto = new SkillDTO();
        dto.setSkillId(1L);
        dto.setSkillName("Spring Boot");

        when(skillService.getSkillById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillId").value(1))
                .andExpect(jsonPath("$.skillName").value("Spring Boot"));
    }

    @Test
    void testGetAllSkills() throws Exception {

        SkillDTO dto = new SkillDTO();
        dto.setSkillId(1L);
        dto.setSkillName("Java");

        Page<SkillDTO> page = new PageImpl<>(Collections.singletonList(dto));

        when(skillService.getAllSkills(
                0,
                10,
                "skillId",
                "asc"))
                .thenReturn(page);

        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].skillId").value(1))
                .andExpect(jsonPath("$.content[0].skillName").value("Java"));
    }

    @Test
    void testUpdateSkill() throws Exception {

        SkillDTO dto = new SkillDTO();
        dto.setSkillId(1L);
        dto.setSkillName("React");

        when(skillService.updateSkill(eq(1L), any(SkillDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillName").value("React"));
    }

    @Test
    void testDeleteSkill() throws Exception {

        doNothing().when(skillService).deleteSkill(1L);

        mockMvc.perform(delete("/api/skills/1"))
                .andExpect(status().isOk());
    }

}