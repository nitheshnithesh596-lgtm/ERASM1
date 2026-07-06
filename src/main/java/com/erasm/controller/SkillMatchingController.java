package com.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.EmployeeSkillMatchDTO;
import com.erasm.service.SkillMatchingService;

@RestController
@RequestMapping("/api/skill-matching")
public class SkillMatchingController {

    @Autowired
    private SkillMatchingService skillMatchingService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{skillName}")
    public ResponseEntity<List<EmployeeSkillMatchDTO>> getEmployeesBySkill(
            @PathVariable String skillName) {

        return ResponseEntity.ok(
                skillMatchingService.getEmployeesBySkill(skillName));
    }
}