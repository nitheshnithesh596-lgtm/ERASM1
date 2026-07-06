package com.erasm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.entity.AuditLog;
import com.erasm.repository.AuditLogRepository;
import com.erasm.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void logAction(
            String action,
            String performedBy,
            String description) {

        AuditLog auditLog = new AuditLog();

        auditLog.setAction(action);
        auditLog.setPerformedBy(performedBy);
        auditLog.setDescription(description);

        auditLogRepository.save(auditLog);
    }
}