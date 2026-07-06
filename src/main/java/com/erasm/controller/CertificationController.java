package com.erasm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.CertificationDTO;
import com.erasm.service.CertificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/certifications")
public class CertificationController {

    private final CertificationService service;

    public CertificationController(CertificationService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<CertificationDTO> create(@Valid @RequestBody CertificationDTO dto) {
        return ResponseEntity.ok(service.saveCertification(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<List<CertificationDTO>> getAll() {
        return ResponseEntity.ok(service.getAllCertifications());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CertificationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCertificationById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteCertification(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}