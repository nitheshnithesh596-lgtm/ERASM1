package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.erasm.dto.SkillDTO;
import com.erasm.entity.Skill;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.SkillRepository;
import com.erasm.serviceimpl.SkillServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @Test
    void testGetSkillById() {

        Skill skill = new Skill();
        skill.setSkillId(1L);
        skill.setSkillName("Java");

        when(skillRepository.findById(1L))
                .thenReturn(Optional.of(skill));

        SkillDTO dto = skillService.getSkillById(1L);

        assertNotNull(dto);
        assertEquals("Java", dto.getSkillName());

        verify(skillRepository).findById(1L);
    }

    @Test
    void testSkillNotFound() {

        when(skillRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> skillService.getSkillById(99L));

        verify(skillRepository).findById(99L);
    }

    @Test
    void testSaveSkill() {

        SkillDTO dto = new SkillDTO();
        dto.setSkillName("Spring Boot");

        Skill skill = new Skill();
        skill.setSkillId(1L);
        skill.setSkillName("Spring Boot");

        when(skillRepository.findBySkillName("Spring Boot"))
                .thenReturn(Optional.empty());

        when(skillRepository.save(any(Skill.class)))
                .thenReturn(skill);

        SkillDTO saved = skillService.saveSkill(dto);

        assertNotNull(saved);
        assertEquals("Spring Boot", saved.getSkillName());

        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void testDuplicateSkill() {

        Skill skill = new Skill();
        skill.setSkillName("Java");

        SkillDTO dto = new SkillDTO();
        dto.setSkillName("Java");

        when(skillRepository.findBySkillName("Java"))
                .thenReturn(Optional.of(skill));

        assertThrows(RuntimeException.class,
                () -> skillService.saveSkill(dto));
    }

    @Test
    void testUpdateSkill() {

        Skill skill = new Skill();
        skill.setSkillId(1L);
        skill.setSkillName("Java");

        SkillDTO dto = new SkillDTO();
        dto.setSkillName("Spring Boot");

        when(skillRepository.findById(1L))
                .thenReturn(Optional.of(skill));

        when(skillRepository.save(any(Skill.class)))
                .thenReturn(skill);

        SkillDTO updated = skillService.updateSkill(1L, dto);

        assertEquals("Spring Boot", updated.getSkillName());

        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void testUpdateSkillNotFound() {

        SkillDTO dto = new SkillDTO();
        dto.setSkillName("AWS");

        when(skillRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> skillService.updateSkill(1L, dto));
    }

    @Test
    void testDeleteSkill() {

        Skill skill = new Skill();
        skill.setSkillId(1L);

        when(skillRepository.findById(1L))
                .thenReturn(Optional.of(skill));

        skillService.deleteSkill(1L);

        verify(skillRepository).delete(skill);
    }

    @Test
    void testDeleteSkillNotFound() {

        when(skillRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> skillService.deleteSkill(1L));
    }
}