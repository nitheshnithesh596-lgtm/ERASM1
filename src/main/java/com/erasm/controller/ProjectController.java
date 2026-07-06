package com.erasm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.ProjectDTO;
import com.erasm.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ProjectDTO> create(
            @Valid @RequestBody ProjectDTO dto) {

        return ResponseEntity.ok(projectService.saveProject(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "projectId") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                projectService.getAllProjects(
                        page,
                        size,
                        sortBy,
                        direction));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO dto) {

        return ResponseEntity.ok(
                projectService.updateProject(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        projectService.deleteProject(id);

        return ResponseEntity.ok("Project deleted successfully");
    }
}