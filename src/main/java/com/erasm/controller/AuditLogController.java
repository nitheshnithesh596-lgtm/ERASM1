package com.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.AuditLogDTO;
import com.erasm.service.AuditLogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auditlogs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogDTO> saveAuditLog(@Valid @RequestBody AuditLogDTO auditLogDTO) {
        AuditLogDTO savedAuditLog = auditLogService.saveAuditLog(auditLogDTO);
        return new ResponseEntity<>(savedAuditLog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDTO> getAuditLogById(@PathVariable("id") Long auditId) {
        AuditLogDTO auditLogDTO = auditLogService.getAuditLogById(auditId);
        return ResponseEntity.ok(auditLogDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllAuditLogs() {
        List<AuditLogDTO> auditLogs = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(auditLogs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditLogDTO> updateAuditLog(@PathVariable("id") Long auditId,
    		@Valid
                                                      @RequestBody AuditLogDTO auditLogDTO) {
        AuditLogDTO updatedAuditLog = auditLogService.updateAuditLog(auditId, auditLogDTO);
        return ResponseEntity.ok(updatedAuditLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuditLog(@PathVariable("id") Long auditId) {
        auditLogService.deleteAuditLog(auditId);
        return ResponseEntity.ok("Audit Log deleted successfully.");
    }
}