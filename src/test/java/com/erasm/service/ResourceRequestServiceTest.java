package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.erasm.dto.ResourceRequestDTO;
import com.erasm.entity.Project;
import com.erasm.entity.ResourceRequest;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.ProjectRepository;
import com.erasm.repository.ResourceRequestRepository;
import com.erasm.serviceimpl.ResourceRequestServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResourceRequestServiceTest {

    @Mock
    private ResourceRequestRepository resourceRequestRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ResourceRequestServiceImpl resourceRequestService;

    @Test
    void testSaveResourceRequest() {

        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("ERASM");

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setProjectId(1L);
        dto.setRequiredSkill("Java");
        dto.setRequiredCount(3);

        ResourceRequest request = new ResourceRequest();
        request.setRequestId(1L);
        request.setRequiredSkill("Java");
        request.setRequiredCount(3);
        request.setProject(project);
        request.setStatus("PENDING");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequestDTO result =
                resourceRequestService.saveResourceRequest(dto);

        assertNotNull(result);

        verify(resourceRequestRepository).save(any(ResourceRequest.class));

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testSaveProjectNotFound() {

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setProjectId(1L);

        when(projectRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> resourceRequestService.saveResourceRequest(dto));
    }

    @Test
    void testGetResourceRequestById() {

        ResourceRequest request = new ResourceRequest();
        request.setRequestId(1L);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        ResourceRequestDTO dto =
                resourceRequestService.getResourceRequestById(1L);

        assertNotNull(dto);

        verify(resourceRequestRepository).findById(1L);
    }

    @Test
    void testResourceRequestNotFound() {

        when(resourceRequestRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> resourceRequestService.getResourceRequestById(99L));
    }

    @Test
    void testGetAllResourceRequests() {

        ResourceRequest request = new ResourceRequest();

        when(resourceRequestRepository.findAll())
                .thenReturn(Arrays.asList(request));

        assertEquals(1,
                resourceRequestService.getAllResourceRequests().size());
    }

    @Test
    void testUpdateResourceRequest() {

        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("ERASM");

        ResourceRequest request = new ResourceRequest();
        request.setRequestId(1L);

        ResourceRequestDTO dto = new ResourceRequestDTO();
        dto.setProjectId(1L);
        dto.setRequiredSkill("Spring Boot");
        dto.setRequiredCount(5);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequestDTO updated =
                resourceRequestService.updateResourceRequest(1L, dto);

        assertNotNull(updated);

        verify(resourceRequestRepository).save(any(ResourceRequest.class));

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testDeleteResourceRequest() {

        ResourceRequest request = new ResourceRequest();
        request.setRequestId(1L);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        resourceRequestService.deleteResourceRequest(1L);

        verify(resourceRequestRepository).delete(request);

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testDeleteResourceRequestNotFound() {

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> resourceRequestService.deleteResourceRequest(1L));
    }

    @Test
    void testApproveRequest() {

        ResourceRequest request = new ResourceRequest();
        request.setRequestId(1L);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequestDTO dto =
                resourceRequestService.approveRequest(1L);

        assertNotNull(dto);
        assertEquals("APPROVED", request.getStatus());

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testRejectRequest() {

        ResourceRequest request = new ResourceRequest();
        request.setRequestId(1L);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequestDTO dto =
                resourceRequestService.rejectRequest(1L);

        assertNotNull(dto);
        assertEquals("REJECTED", request.getStatus());

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testGetPendingRequests() {

        ResourceRequest request = new ResourceRequest();
        request.setStatus("PENDING");

        when(resourceRequestRepository.findByStatus("PENDING"))
                .thenReturn(Collections.singletonList(request));

        assertEquals(1,
                resourceRequestService.getPendingRequests().size());
    }
}