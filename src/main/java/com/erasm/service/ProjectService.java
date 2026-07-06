package com.erasm.service;

import org.springframework.data.domain.Page;

import com.erasm.dto.ProjectDTO;

public interface ProjectService {

    ProjectDTO saveProject(ProjectDTO projectDTO);

    ProjectDTO getProjectById(Long projectId);

    Page<ProjectDTO> getAllProjects(
            int page,
            int size,
            String sortBy,
            String direction);

    ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO);

    void deleteProject(Long projectId);
}