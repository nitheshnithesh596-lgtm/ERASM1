package com.erasm.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.EmployeeSkillMatchDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.EmployeeSkill;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeSkillRepository;
import com.erasm.service.SkillMatchingService;

@Service
public class SkillMatchingServiceImpl implements SkillMatchingService {

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Override
    public List<EmployeeSkillMatchDTO> getEmployeesBySkill(String skillName) {

        List<EmployeeSkill> employeeSkills =
                employeeSkillRepository.findBySkillSkillNameIgnoreCase(skillName);

        List<EmployeeSkillMatchDTO> response = new ArrayList<>();

        for (EmployeeSkill employeeSkill : employeeSkills) {

            List<Allocation> allocations =
                    allocationRepository.findByEmployeeEmployeeId(
                            employeeSkill.getEmployee().getEmployeeId());

            int utilization = 0;

            for (Allocation allocation : allocations) {
                utilization += allocation.getAllocationPercentage();
            }

            EmployeeSkillMatchDTO dto = new EmployeeSkillMatchDTO();

            dto.setEmployeeId(employeeSkill.getEmployee().getEmployeeId());

            dto.setEmployeeName(
                    employeeSkill.getEmployee().getFirstName() + " " +
                    employeeSkill.getEmployee().getLastName()
            );

            dto.setSkillName(employeeSkill.getSkill().getSkillName());

            dto.setSkillLevel(employeeSkill.getSkillLevel());

            dto.setExperienceYears(employeeSkill.getExperienceYears());

            dto.setUtilization(utilization);

            response.add(dto);
        }

        return response;
    }
}