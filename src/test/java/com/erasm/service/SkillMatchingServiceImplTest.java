package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erasm.dto.EmployeeSkillMatchDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.EmployeeSkill;
import com.erasm.entity.Skill;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeSkillRepository;
import com.erasm.serviceimpl.SkillMatchingServiceImpl;

@ExtendWith(MockitoExtension.class)
class SkillMatchingServiceImplTest {

    @Mock
    private EmployeeSkillRepository employeeSkillRepository;

    @Mock
    private AllocationRepository allocationRepository;

    @InjectMocks
    private SkillMatchingServiceImpl skillMatchingService;

    @Test
    void testGetEmployeesBySkill() {

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        Skill skill = new Skill();
        skill.setSkillName("Java");

        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkill.setSkillLevel("Advanced");
        employeeSkill.setExperienceYears(5);

        Allocation allocation1 = new Allocation();
        allocation1.setAllocationPercentage(60);

        Allocation allocation2 = new Allocation();
        allocation2.setAllocationPercentage(40);

        when(employeeSkillRepository.findBySkillSkillNameIgnoreCase("Java"))
                .thenReturn(Arrays.asList(employeeSkill));

        when(allocationRepository.findByEmployeeEmployeeId(1L))
                .thenReturn(Arrays.asList(allocation1, allocation2));

        List<EmployeeSkillMatchDTO> result =
                skillMatchingService.getEmployeesBySkill("Java");

        assertEquals(1, result.size());

        EmployeeSkillMatchDTO dto = result.get(0);

        assertEquals(1L, dto.getEmployeeId());
        assertEquals("John Doe", dto.getEmployeeName());
        assertEquals("Java", dto.getSkillName());
        assertEquals("Advanced", dto.getSkillLevel());
        assertEquals(5, dto.getExperienceYears());
        assertEquals(100, dto.getUtilization());
    }

    @Test
    void testGetEmployeesBySkill_NoEmployees() {

        when(employeeSkillRepository.findBySkillSkillNameIgnoreCase("Python"))
                .thenReturn(List.of());

        List<EmployeeSkillMatchDTO> result =
                skillMatchingService.getEmployeesBySkill("Python");

        assertTrue(result.isEmpty());
    }
}