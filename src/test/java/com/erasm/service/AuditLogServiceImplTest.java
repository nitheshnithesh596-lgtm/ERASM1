package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.erasm.dto.AuditLogDTO;
import com.erasm.entity.AuditLog;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.AuditLogRepository;
import com.erasm.serviceimpl.AuditLogServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceImplTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogServiceImpl auditLogService;

    private AuditLog auditLog;
    private AuditLogDTO auditLogDTO;

    @BeforeEach
    void setUp() {

        auditLog = new AuditLog();
        auditLog.setAuditId(1L);
        auditLog.setAction("CREATE");
        auditLog.setPerformedBy("Admin");
        auditLog.setPerformedAt(LocalDateTime.now());
        auditLog.setDescription("Employee Created");

        auditLogDTO = new AuditLogDTO();
        auditLogDTO.setAuditId(1L);
        auditLogDTO.setAction("CREATE");
        auditLogDTO.setPerformedBy("Admin");
        auditLogDTO.setPerformedAt(auditLog.getPerformedAt());
        auditLogDTO.setDescription("Employee Created");
    }

    @Test
    void testSaveAuditLog() {

        when(auditLogRepository.save(any(AuditLog.class)))
                .thenReturn(auditLog);

        AuditLogDTO saved = auditLogService.saveAuditLog(auditLogDTO);

        assertNotNull(saved);
        assertEquals("CREATE", saved.getAction());

        verify(auditLogRepository, times(1))
                .save(any(AuditLog.class));
    }

    @Test
    void testGetAuditLogById() {

        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.of(auditLog));

        AuditLogDTO result =
                auditLogService.getAuditLogById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getAuditId());

        verify(auditLogRepository)
                .findById(1L);
    }

    @Test
    void testGetAuditLogById_NotFound() {

        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> auditLogService.getAuditLogById(1L));

        verify(auditLogRepository)
                .findById(1L);
    }

    @Test
    void testGetAllAuditLogs() {

        when(auditLogRepository.findAll())
                .thenReturn(Arrays.asList(auditLog));

        List<AuditLogDTO> list =
                auditLogService.getAllAuditLogs();

        assertEquals(1, list.size());

        verify(auditLogRepository)
                .findAll();
    }

    @Test
    void testUpdateAuditLog() {

        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.of(auditLog));

        when(auditLogRepository.save(any(AuditLog.class)))
                .thenReturn(auditLog);

        AuditLogDTO updated =
                auditLogService.updateAuditLog(1L, auditLogDTO);

        assertNotNull(updated);
        assertEquals("CREATE", updated.getAction());

        verify(auditLogRepository)
                .findById(1L);

        verify(auditLogRepository)
                .save(any(AuditLog.class));
    }

    @Test
    void testUpdateAuditLog_NotFound() {

        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> auditLogService.updateAuditLog(1L, auditLogDTO));

        verify(auditLogRepository)
                .findById(1L);
    }

    @Test
    void testDeleteAuditLog() {

        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.of(auditLog));

        doNothing().when(auditLogRepository)
                .delete(auditLog);

        auditLogService.deleteAuditLog(1L);

        verify(auditLogRepository)
                .delete(auditLog);
    }

    @Test
    void testDeleteAuditLog_NotFound() {

        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> auditLogService.deleteAuditLog(1L));

        verify(auditLogRepository)
                .findById(1L);
    }
}