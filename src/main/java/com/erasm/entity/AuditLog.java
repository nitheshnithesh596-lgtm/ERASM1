package com.erasm.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    private String action;

    private String performedBy;

    private LocalDateTime performedAt;

    private String description;

    public AuditLog() {
    }

    public AuditLog(Long auditId, String action, String performedBy,
            LocalDateTime performedAt, String description) {

        this.auditId = auditId;
        this.action = action;
        this.performedBy = performedBy;
        this.performedAt = performedAt;
        this.description = description;
    }

    @PrePersist
    public void onCreate() {
        if (this.performedAt == null) {
            this.performedAt = LocalDateTime.now();
        }
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}