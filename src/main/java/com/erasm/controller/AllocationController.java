package com.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.AllocationDTO;
import com.erasm.service.AllocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {

    @Autowired
    private AllocationService allocationService;
    private Integer billableHours;

    private Integer totalHours;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AllocationDTO> create(@Valid @RequestBody AllocationDTO dto) {
        return ResponseEntity.ok(allocationService.saveAllocation(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AllocationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(allocationService.getAllocationById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AllocationDTO>> getAll() {
        return ResponseEntity.ok(allocationService.getAllAllocations());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AllocationDTO> update(@PathVariable Long id,
    		@Valid
                                                @RequestBody AllocationDTO dto) {
        return ResponseEntity.ok(allocationService.updateAllocation(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
        return ResponseEntity.ok("Allocation deleted successfully");
    }
}