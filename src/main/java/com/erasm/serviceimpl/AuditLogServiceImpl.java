package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.AuditLogDTO;
import com.erasm.entity.AuditLog;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.AuditLogMapper;
import com.erasm.repository.AuditLogRepository;
import com.erasm.service.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public AuditLogDTO saveAuditLog(AuditLogDTO auditLogDTO) {

        AuditLog auditLog = AuditLogMapper.toEntity(auditLogDTO);

        AuditLog savedAuditLog = auditLogRepository.save(auditLog);

        return AuditLogMapper.toDTO(savedAuditLog);
    }

    @Override
    public AuditLogDTO getAuditLogById(Long auditId) {

        AuditLog auditLog = auditLogRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Audit Log not found with id : " + auditId));

        return AuditLogMapper.toDTO(auditLog);
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs() {

        return auditLogRepository.findAll()
                .stream()
                .map(AuditLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuditLogDTO updateAuditLog(Long auditId, AuditLogDTO auditLogDTO) {

        AuditLog auditLog = auditLogRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Audit Log not found with id : " + auditId));

        auditLog.setAction(auditLogDTO.getAction());
        auditLog.setPerformedBy(auditLogDTO.getPerformedBy());
        auditLog.setPerformedAt(auditLogDTO.getPerformedAt());
        auditLog.setDescription(auditLogDTO.getDescription());

        AuditLog updatedAuditLog = auditLogRepository.save(auditLog);

        return AuditLogMapper.toDTO(updatedAuditLog);
    }

    @Override
    public void deleteAuditLog(Long auditId) {

        AuditLog auditLog = auditLogRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Audit Log not found with id : " + auditId));

        auditLogRepository.delete(auditLog);
    }
}