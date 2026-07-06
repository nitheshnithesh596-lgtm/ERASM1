package com.erasm.mapper;

import com.erasm.dto.AllocationDTO;
import com.erasm.entity.Allocation;

public class AllocationMapper {

    public static AllocationDTO toDTO(Allocation allocation) {

        if (allocation == null) {
            return null;
        }

        AllocationDTO dto = new AllocationDTO();

        dto.setAllocationId(allocation.getAllocationId());
        dto.setAllocationPercentage(allocation.getAllocationPercentage());
        dto.setAllocationDate(allocation.getAllocationDate());
        dto.setReleaseDate(allocation.getReleaseDate());

        dto.setBillableHours(allocation.getBillableHours());
        dto.setTotalHours(allocation.getTotalHours());

        if (allocation.getEmployee() != null) {
            dto.setEmployeeId(allocation.getEmployee().getEmployeeId());
        }

        if (allocation.getProject() != null) {
            dto.setProjectId(allocation.getProject().getProjectId());
        }

        return dto;
    }

    public static Allocation toEntity(AllocationDTO dto) {

        if (dto == null) {
            return null;
        }

        Allocation allocation = new Allocation();

        allocation.setAllocationId(dto.getAllocationId());
        allocation.setAllocationPercentage(dto.getAllocationPercentage());
        allocation.setAllocationDate(dto.getAllocationDate());
        allocation.setReleaseDate(dto.getReleaseDate());

        allocation.setBillableHours(dto.getBillableHours());
        allocation.setTotalHours(dto.getTotalHours());

        return allocation;
    }
}