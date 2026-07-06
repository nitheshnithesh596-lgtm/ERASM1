package com.erasm.service;

import java.util.List;
import com.erasm.dto.AuditLogDTO;

public interface AuditLogService {

    AuditLogDTO saveAuditLog(AuditLogDTO auditLogDTO);

    AuditLogDTO getAuditLogById(Long auditId);

    List<AuditLogDTO> getAllAuditLogs();

    AuditLogDTO updateAuditLog(Long auditId, AuditLogDTO auditLogDTO);

    void deleteAuditLog(Long auditId);
}