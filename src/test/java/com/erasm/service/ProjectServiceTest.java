package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.erasm.dto.ProjectDTO;
import com.erasm.entity.Project;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.ProjectRepository;
import com.erasm.serviceimpl.ProjectServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void testGetProjectById() {

        Project project = new Project();

        project.setProjectId(1L);
        project.setProjectName("ERASM");
        project.setClientName("OpenAI");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        ProjectDTO dto = projectService.getProjectById(1L);

        assertNotNull(dto);
        assertEquals("ERASM", dto.getProjectName());
        assertEquals("OpenAI", dto.getClientName());

        verify(projectRepository).findById(1L);
    }

    @Test
    void testProjectNotFound() {

        when(projectRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> projectService.getProjectById(99L));

        verify(projectRepository).findById(99L);
    }

    @Test
    void testDeleteProject() {

        Project project = new Project();

        project.setProjectId(1L);
        project.setProjectName("ERASM");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        projectService.deleteProject(1L);

        verify(projectRepository).delete(project);

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testDeleteProjectNotFound() {

        when(projectRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> projectService.deleteProject(1L));
    }

    @Test
    void testSaveDuplicateProject() {

        ProjectDTO dto = new ProjectDTO();

        dto.setProjectName("ERASM");

        when(projectRepository.existsByProjectName("ERASM"))
                .thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> projectService.saveProject(dto));
    }

    @Test
    void testUpdateProjectNotFound() {

        ProjectDTO dto = new ProjectDTO();

        when(projectRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> projectService.updateProject(1L, dto));
    }
}