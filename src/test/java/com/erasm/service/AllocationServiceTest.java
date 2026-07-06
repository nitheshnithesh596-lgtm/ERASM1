package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.erasm.dto.AllocationDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;
import com.erasm.serviceimpl.AllocationServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AllocationServiceTest {

    @Mock
    private AllocationRepository allocationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AllocationServiceImpl allocationService;

    @Test
    void testSaveAllocation() {

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmployeeCode("EMP001");

        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("ERASM");

        AllocationDTO dto = new AllocationDTO();
        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setAllocationPercentage(40);
        dto.setAllocationDate(LocalDate.now());
        dto.setReleaseDate(LocalDate.now().plusDays(30));

        Allocation allocation = new Allocation();
        allocation.setAllocationId(1L);
        allocation.setAllocationPercentage(40);
        allocation.setEmployee(employee);
        allocation.setProject(project);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(allocationRepository.findByEmployee(employee))
                .thenReturn(Collections.emptyList());

        when(allocationRepository.save(any(Allocation.class)))
                .thenReturn(allocation);

        AllocationDTO result = allocationService.saveAllocation(dto);

        assertNotNull(result);

        verify(allocationRepository).save(any(Allocation.class));

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testEmployeeNotFound() {

        AllocationDTO dto = new AllocationDTO();
        dto.setEmployeeId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> allocationService.saveAllocation(dto));
    }

    @Test
    void testProjectNotFound() {

        Employee employee = new Employee();

        AllocationDTO dto = new AllocationDTO();
        dto.setEmployeeId(1L);
        dto.setProjectId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> allocationService.saveAllocation(dto));
    }

    @Test
    void testReleaseDateValidation() {

        Employee employee = new Employee();
        Project project = new Project();

        AllocationDTO dto = new AllocationDTO();

        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setAllocationPercentage(30);
        dto.setAllocationDate(LocalDate.now());
        dto.setReleaseDate(LocalDate.now().minusDays(1));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        assertThrows(RuntimeException.class,
                () -> allocationService.saveAllocation(dto));
    }

    @Test
    void testAllocationExceeds100() {

        Employee employee = new Employee();
        Project project = new Project();

        Allocation oldAllocation = new Allocation();
        oldAllocation.setAllocationPercentage(80);

        AllocationDTO dto = new AllocationDTO();

        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setAllocationPercentage(30);
        dto.setAllocationDate(LocalDate.now());
        dto.setReleaseDate(LocalDate.now().plusDays(20));

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(allocationRepository.findByEmployee(employee))
                .thenReturn(Arrays.asList(oldAllocation));

        assertThrows(RuntimeException.class,
                () -> allocationService.saveAllocation(dto));
    }

    @Test
    void testGetAllocationById() {

        Allocation allocation = new Allocation();
        allocation.setAllocationId(1L);

        when(allocationRepository.findById(1L))
                .thenReturn(Optional.of(allocation));

        AllocationDTO dto = allocationService.getAllocationById(1L);

        assertNotNull(dto);

        verify(allocationRepository).findById(1L);
    }

    @Test
    void testAllocationNotFound() {

        when(allocationRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> allocationService.getAllocationById(99L));
    }

    @Test
    void testGetAllAllocations() {

        Allocation allocation = new Allocation();

        when(allocationRepository.findAll())
                .thenReturn(Arrays.asList(allocation));

        assertEquals(1,
                allocationService.getAllAllocations().size());
    }

    @Test
    void testDeleteAllocation() {

        Allocation allocation = new Allocation();
        allocation.setAllocationId(1L);

        when(allocationRepository.findById(1L))
                .thenReturn(Optional.of(allocation));

        allocationService.deleteAllocation(1L);

        verify(allocationRepository).delete(allocation);

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testDeleteAllocationNotFound() {

        when(allocationRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> allocationService.deleteAllocation(1L));
    }
}