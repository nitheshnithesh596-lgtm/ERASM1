package com.erasm.service;
import com.erasm.service.AuditService;
public interface AuditService {

    void logAction(
            String action,
            String performedBy,
            String description);

}