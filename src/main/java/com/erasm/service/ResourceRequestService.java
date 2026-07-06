package com.erasm.service;

import java.util.List;

import com.erasm.dto.ResourceRequestDTO;

public interface ResourceRequestService {

    ResourceRequestDTO saveResourceRequest(ResourceRequestDTO resourceRequestDTO);

    ResourceRequestDTO getResourceRequestById(Long requestId);

    List<ResourceRequestDTO> getAllResourceRequests();

    ResourceRequestDTO updateResourceRequest(Long requestId,
                                             ResourceRequestDTO resourceRequestDTO);

    void deleteResourceRequest(Long requestId);

    ResourceRequestDTO approveRequest(Long requestId);

    ResourceRequestDTO rejectRequest(Long requestId);

    List<ResourceRequestDTO> getPendingRequests();

}