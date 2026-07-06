package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.AllocationDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.AllocationMapper;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;
import com.erasm.service.AllocationService;
import com.erasm.service.AuditService;

@Service
public class AllocationServiceImpl implements AllocationService {

    private static final Logger logger =
            LoggerFactory.getLogger(AllocationServiceImpl.class);

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public AllocationDTO saveAllocation(AllocationDTO allocationDTO) {

        Employee employee = employeeRepository.findById(allocationDTO.getEmployeeId())
                .orElseThrow(() -> {
                    logger.error("Employee not found while allocating resource.");
                    return new ResourceNotFoundException(
                            "Employee not found with ID : "
                                    + allocationDTO.getEmployeeId());
                });

        Project project = projectRepository.findById(allocationDTO.getProjectId())
                .orElseThrow(() -> {
                    logger.error("Project not found while allocating resource.");
                    return new ResourceNotFoundException(
                            "Project not found with ID : "
                                    + allocationDTO.getProjectId());
                });

        if (allocationDTO.getReleaseDate()
                .isBefore(allocationDTO.getAllocationDate())) {

            logger.warn("Invalid allocation request.");

            throw new RuntimeException(
                    "Release date cannot be before allocation date.");
        }

        List<Allocation> allocations =
                allocationRepository.findByEmployee(employee);

        int totalAllocation = allocations.stream()
                .mapToInt(Allocation::getAllocationPercentage)
                .sum();

        if (totalAllocation
                + allocationDTO.getAllocationPercentage() > 100) {

            logger.warn("Employee allocation exceeded 100%.");

            throw new RuntimeException(
                    "Employee allocation exceeds 100%.");
        }

        Allocation allocation =
                AllocationMapper.toEntity(allocationDTO);

        allocation.setEmployee(employee);
        allocation.setProject(project);

        Allocation savedAllocation =
                allocationRepository.save(allocation);

        logger.info("Resource allocation created successfully.");

        auditService.logAction(
                "CREATE_ALLOCATION",
                "ADMIN",
                "Allocated employee "
                        + employee.getEmployeeCode()
                        + " to project "
                        + project.getProjectName());

        return AllocationMapper.toDTO(savedAllocation);
    }

    @Override
    public AllocationDTO getAllocationById(Long allocationId) {

        Allocation allocation =
                allocationRepository.findById(allocationId)
                        .orElseThrow(() -> {
                            logger.error("Allocation not found.");
                            return new ResourceNotFoundException(
                                    "Allocation not found with ID : "
                                            + allocationId);
                        });

        return AllocationMapper.toDTO(allocation);
    }

    @Override
    public List<AllocationDTO> getAllAllocations() {

        return allocationRepository.findAll()
                .stream()
                .map(AllocationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AllocationDTO updateAllocation(
            Long allocationId,
            AllocationDTO allocationDTO) {

        Allocation allocation =
                allocationRepository.findById(allocationId)
                        .orElseThrow(() -> {
                            logger.error("Allocation not found.");
                            return new ResourceNotFoundException(
                                    "Allocation not found with ID : "
                                            + allocationId);
                        });

        Employee employee =
                employeeRepository.findById(allocationDTO.getEmployeeId())
                        .orElseThrow(() -> {
                            logger.error("Employee not found while updating allocation.");
                            return new ResourceNotFoundException(
                                    "Employee not found with ID : "
                                            + allocationDTO.getEmployeeId());
                        });

        Project project =
                projectRepository.findById(allocationDTO.getProjectId())
                        .orElseThrow(() -> {
                            logger.error("Project not found while updating allocation.");
                            return new ResourceNotFoundException(
                                    "Project not found with ID : "
                                            + allocationDTO.getProjectId());
                        });

        if (allocationDTO.getReleaseDate()
                .isBefore(allocationDTO.getAllocationDate())) {

            logger.warn("Invalid allocation update request.");

            throw new RuntimeException(
                    "Release date cannot be before allocation date.");
        }
        List<Allocation> allocations =
                allocationRepository.findByEmployee(employee);

        int totalAllocation = allocations.stream()
                .filter(a -> !a.getAllocationId().equals(allocationId))
                .mapToInt(Allocation::getAllocationPercentage)
                .sum();

        if (totalAllocation
                + allocationDTO.getAllocationPercentage() > 100) {

            logger.warn("Employee allocation exceeded 100%.");

            throw new RuntimeException(
                    "Employee allocation exceeds 100%.");
        }

        allocation.setAllocationPercentage(
                allocationDTO.getAllocationPercentage());

        allocation.setAllocationDate(
                allocationDTO.getAllocationDate());

        allocation.setReleaseDate(
                allocationDTO.getReleaseDate());

        allocation.setEmployee(employee);
        allocation.setProject(project);

        Allocation updatedAllocation =
                allocationRepository.save(allocation);

        logger.info("Resource allocation updated successfully.");

        auditService.logAction(
                "UPDATE_ALLOCATION",
                "ADMIN",
                "Updated allocation for employee "
                        + employee.getEmployeeCode()
                        + " in project "
                        + project.getProjectName());

        return AllocationMapper.toDTO(updatedAllocation);
    }

    @Override
    public void deleteAllocation(Long allocationId) {

        Allocation allocation =
                allocationRepository.findById(allocationId)
                        .orElseThrow(() -> {
                            logger.error("Allocation not found.");
                            return new ResourceNotFoundException(
                                    "Allocation not found with ID : "
                                            + allocationId);
                        });

        logger.info("Resource allocation deleted successfully.");

        auditService.logAction(
                "DELETE_ALLOCATION",
                "ADMIN",
                "Deleted allocation ID : "
                        + allocation.getAllocationId());

        allocationRepository.delete(allocation);
    }
}