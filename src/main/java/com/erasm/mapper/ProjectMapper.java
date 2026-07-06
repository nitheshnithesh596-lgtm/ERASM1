package com.erasm.mapper;

import com.erasm.dto.ProjectDTO;
import com.erasm.entity.Project;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {

        if (project == null) {
            return null;
        }

        ProjectDTO dto = new ProjectDTO();

        dto.setProjectId(project.getProjectId());
        dto.setProjectName(project.getProjectName());
        dto.setClientName(project.getClientName());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setTechnologyStack(project.getTechnologyStack());
        dto.setBudget(project.getBudget());
        dto.setStatus(project.getStatus());

        return dto;
    }

    public static Project toEntity(ProjectDTO dto) {

        if (dto == null) {
            return null;
        }

        Project project = new Project();

        project.setProjectId(dto.getProjectId());
        project.setProjectName(dto.getProjectName());
        project.setClientName(dto.getClientName());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setTechnologyStack(dto.getTechnologyStack());
        project.setBudget(dto.getBudget());
        project.setStatus(dto.getStatus());

        return project;
    }
}