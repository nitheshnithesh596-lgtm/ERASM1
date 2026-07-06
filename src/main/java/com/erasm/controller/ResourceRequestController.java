package com.erasm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.ResourceRequestDTO;
import com.erasm.service.ResourceRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/resource-requests")
public class ResourceRequestController {

    private final ResourceRequestService service;

    public ResourceRequestController(ResourceRequestService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<ResourceRequestDTO> create(
           @Valid @RequestBody ResourceRequestDTO dto) {

        return ResponseEntity.ok(
                service.saveResourceRequest(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<ResourceRequestDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getResourceRequestById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<List<ResourceRequestDTO>> getAll() {

        return ResponseEntity.ok(
                service.getAllResourceRequests());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/pending")
    public ResponseEntity<List<ResourceRequestDTO>> getPending() {

        return ResponseEntity.ok(
                service.getPendingRequests());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ResourceRequestDTO> approve(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.approveRequest(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ResourceRequestDTO> reject(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.rejectRequest(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        service.deleteResourceRequest(id);

        return ResponseEntity.ok("Deleted Successfully");
    }

}