package com.erasm.mapper;

import com.erasm.dto.AuditLogDTO;
import com.erasm.entity.AuditLog;

public class AuditLogMapper {

    public static AuditLogDTO toDTO(AuditLog auditLog) {

        if (auditLog == null) {
            return null;
        }

        AuditLogDTO dto = new AuditLogDTO();

        dto.setAuditId(auditLog.getAuditId());
        dto.setAction(auditLog.getAction());
        dto.setPerformedBy(auditLog.getPerformedBy());
        dto.setPerformedAt(auditLog.getPerformedAt());
        dto.setDescription(auditLog.getDescription());

        return dto;
    }

    public static AuditLog toEntity(AuditLogDTO dto) {

        if (dto == null) {
            return null;
        }

        AuditLog auditLog = new AuditLog();

        auditLog.setAuditId(dto.getAuditId());
        auditLog.setAction(dto.getAction());
        auditLog.setPerformedBy(dto.getPerformedBy());
        auditLog.setPerformedAt(dto.getPerformedAt());
        auditLog.setDescription(dto.getDescription());

        return auditLog;
    }
}