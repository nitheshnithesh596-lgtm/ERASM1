package com.erasm.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.SkillDTO;
import com.erasm.service.SkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SkillDTO> create(
            @Valid @RequestBody SkillDTO dto) {

        return ResponseEntity.ok(service.saveSkill(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Page<SkillDTO>> getAllSkills(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "skillId") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                service.getAllSkills(
                        page,
                        size,
                        sortBy,
                        direction));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getSkillById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SkillDTO dto) {

        return ResponseEntity.ok(service.updateSkill(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        service.deleteSkill(id);

        return ResponseEntity.ok("Skill deleted successfully.");
    }
}