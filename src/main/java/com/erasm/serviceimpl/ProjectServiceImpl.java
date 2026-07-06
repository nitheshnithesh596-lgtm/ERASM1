package com.erasm.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.erasm.dto.ProjectDTO;
import com.erasm.entity.Project;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.ProjectMapper;
import com.erasm.repository.ProjectRepository;
import com.erasm.service.AuditService;
import com.erasm.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {

        if (projectRepository.existsByProjectName(projectDTO.getProjectName())) {

            logger.warn("Invalid project creation request.");

            throw new RuntimeException("Project already exists.");
        }

        Project project = ProjectMapper.toEntity(projectDTO);

        Project savedProject = projectRepository.save(project);

        logger.info("Project created successfully.");

        auditService.logAction(
                "CREATE_PROJECT",
                "ADMIN",
                "Created project : " + savedProject.getProjectName());

        return ProjectMapper.toDTO(savedProject);
    }

    @Override
    public Page<ProjectDTO> getAllProjects(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return projectRepository.findAll(pageable)
                .map(ProjectMapper::toDTO);
    }

    @Override
    public ProjectDTO getProjectById(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.error("Project not found.");
                    return new ResourceNotFoundException(
                            "Project not found with ID : " + projectId);
                });

        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.error("Project not found.");
                    return new ResourceNotFoundException(
                            "Project not found with ID : " + projectId);
                });

        if (!project.getProjectName().equals(projectDTO.getProjectName())
                && projectRepository.existsByProjectName(projectDTO.getProjectName())) {

            logger.warn("Invalid project update request.");

            throw new RuntimeException("Project already exists.");
        }

        project.setProjectName(projectDTO.getProjectName());
        project.setClientName(projectDTO.getClientName());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setTechnologyStack(projectDTO.getTechnologyStack());
        project.setBudget(projectDTO.getBudget());
        project.setStatus(projectDTO.getStatus());

        Project updatedProject = projectRepository.save(project);

        auditService.logAction(
                "UPDATE_PROJECT",
                "ADMIN",
                "Updated project : " + updatedProject.getProjectName());

        return ProjectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.error("Project not found.");
                    return new ResourceNotFoundException(
                            "Project not found with ID : " + projectId);
                });

        auditService.logAction(
                "DELETE_PROJECT",
                "ADMIN",
                "Deleted project : " + project.getProjectName());

        projectRepository.delete(project);
    }
}