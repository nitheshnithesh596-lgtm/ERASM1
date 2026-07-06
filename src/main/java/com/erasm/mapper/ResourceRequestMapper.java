package com.erasm.mapper;

import com.erasm.dto.ResourceRequestDTO;
import com.erasm.entity.ResourceRequest;

public class ResourceRequestMapper {

    public static ResourceRequestDTO toDTO(ResourceRequest request) {

        if (request == null) {
            return null;
        }

        ResourceRequestDTO dto = new ResourceRequestDTO();

        dto.setRequestId(request.getRequestId());
        dto.setRequiredSkill(request.getRequiredSkill());
        dto.setRequiredCount(request.getRequiredCount());
        dto.setStatus(request.getStatus());

        if (request.getProject() != null) {
            dto.setProjectId(request.getProject().getProjectId());
        }

        return dto;
    }

    public static ResourceRequest toEntity(ResourceRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        ResourceRequest request = new ResourceRequest();

        request.setRequestId(dto.getRequestId());
        request.setRequiredSkill(dto.getRequiredSkill());
        request.setRequiredCount(dto.getRequiredCount());
        request.setStatus(dto.getStatus());

        return request;
    }

}