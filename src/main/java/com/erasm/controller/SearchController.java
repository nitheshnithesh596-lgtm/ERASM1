package com.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.CertificationDTO;
import com.erasm.dto.EmployeeDTO;
import com.erasm.dto.ProjectDTO;
import com.erasm.dto.SkillDTO;
import com.erasm.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(
            @RequestParam String keyword) {

        return ResponseEntity.ok(searchService.searchEmployees(keyword));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> searchProjects(
            @RequestParam String keyword) {

        return ResponseEntity.ok(searchService.searchProjects(keyword));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/skills")
    public ResponseEntity<List<SkillDTO>> searchSkills(
            @RequestParam String keyword) {

        return ResponseEntity.ok(searchService.searchSkills(keyword));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/certifications")
    public ResponseEntity<List<CertificationDTO>> searchCertifications(
            @RequestParam String keyword) {

        return ResponseEntity.ok(searchService.searchCertifications(keyword));
    }

}