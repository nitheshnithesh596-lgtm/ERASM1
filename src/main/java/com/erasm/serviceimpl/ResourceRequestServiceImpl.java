package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.ResourceRequestDTO;
import com.erasm.entity.Project;
import com.erasm.entity.ResourceRequest;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.ResourceRequestMapper;
import com.erasm.repository.ProjectRepository;
import com.erasm.repository.ResourceRequestRepository;
import com.erasm.service.AuditService;
import com.erasm.service.ResourceRequestService;

@Service
public class ResourceRequestServiceImpl implements ResourceRequestService {

    @Autowired
    private ResourceRequestRepository resourceRequestRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public ResourceRequestDTO saveResourceRequest(ResourceRequestDTO dto) {

        ResourceRequest request =
                ResourceRequestMapper.toEntity(dto);

        Project project =
                projectRepository.findById(dto.getProjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with ID : "
                                                + dto.getProjectId()));

        request.setProject(project);
        request.setStatus("PENDING");

        ResourceRequest saved =
                resourceRequestRepository.save(request);

        auditService.logAction(
                "CREATE_RESOURCE_REQUEST",
                "ADMIN",
                "Created resource request for project "
                        + project.getProjectName());

        return ResourceRequestMapper.toDTO(saved);
    }

    @Override
    public List<ResourceRequestDTO> getAllResourceRequests() {

        return resourceRequestRepository.findAll()
                .stream()
                .map(ResourceRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceRequestDTO getResourceRequestById(Long id) {

        ResourceRequest request =
                resourceRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resource Request not found with ID : "
                                                + id));

        return ResourceRequestMapper.toDTO(request);
    }
    @Override
    public ResourceRequestDTO updateResourceRequest(
            Long id,
            ResourceRequestDTO dto) {

        ResourceRequest request =
                resourceRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resource Request not found with ID : "
                                                + id));

        request.setRequiredSkill(dto.getRequiredSkill());
        request.setRequiredCount(dto.getRequiredCount());

        Project project =
                projectRepository.findById(dto.getProjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with ID : "
                                                + dto.getProjectId()));

        request.setProject(project);

        ResourceRequest updated =
                resourceRequestRepository.save(request);

        auditService.logAction(
                "UPDATE_RESOURCE_REQUEST",
                "ADMIN",
                "Updated resource request for project "
                        + project.getProjectName());

        return ResourceRequestMapper.toDTO(updated);
    }

    @Override
    public void deleteResourceRequest(Long id) {

        ResourceRequest request =
                resourceRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resource Request not found with ID : "
                                                + id));

        auditService.logAction(
                "DELETE_RESOURCE_REQUEST",
                "ADMIN",
                "Deleted resource request ID : "
                        + request.getRequestId());

        resourceRequestRepository.delete(request);
    }

    @Override
    public ResourceRequestDTO approveRequest(Long id) {

        ResourceRequest request =
                resourceRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resource Request not found with ID : "
                                                + id));

        request.setStatus("APPROVED");

        ResourceRequest approved =
                resourceRequestRepository.save(request);

        auditService.logAction(
                "APPROVE_RESOURCE_REQUEST",
                "ADMIN",
                "Approved resource request ID : "
                        + approved.getRequestId());

        return ResourceRequestMapper.toDTO(approved);
    }

    @Override
    public ResourceRequestDTO rejectRequest(Long id) {

        ResourceRequest request =
                resourceRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resource Request not found with ID : "
                                                + id));

        request.setStatus("REJECTED");

        ResourceRequest rejected =
                resourceRequestRepository.save(request);

        auditService.logAction(
                "REJECT_RESOURCE_REQUEST",
                "ADMIN",
                "Rejected resource request ID : "
                        + rejected.getRequestId());

        return ResourceRequestMapper.toDTO(rejected);
    }

    @Override
    public List<ResourceRequestDTO> getPendingRequests() {

        return resourceRequestRepository.findByStatus("PENDING")
                .stream()
                .map(ResourceRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
}