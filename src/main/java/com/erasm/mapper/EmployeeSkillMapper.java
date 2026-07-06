package com.erasm.mapper;

import com.erasm.dto.EmployeeSkillDTO;
import com.erasm.entity.EmployeeSkill;

public class EmployeeSkillMapper {

    
    public static EmployeeSkillDTO toDTO(EmployeeSkill employeeSkill) {

        if (employeeSkill == null) {
            return null;
        }

        EmployeeSkillDTO dto = new EmployeeSkillDTO();

        dto.setEmployeeSkillId(employeeSkill.getEmployeeSkillId());

        if (employeeSkill.getEmployee() != null) {
            dto.setEmployeeId(employeeSkill.getEmployee().getEmployeeId());
        }

        if (employeeSkill.getSkill() != null) {
            dto.setSkillId(employeeSkill.getSkill().getSkillId());
        }

        dto.setSkillLevel(employeeSkill.getSkillLevel());
        dto.setExperienceYears(employeeSkill.getExperienceYears());

        return dto;
    }

   
    public static EmployeeSkill toEntity(EmployeeSkillDTO dto) {

        if (dto == null) {
            return null;
        }

        EmployeeSkill employeeSkill = new EmployeeSkill();

        employeeSkill.setEmployeeSkillId(dto.getEmployeeSkillId());
        employeeSkill.setSkillLevel(dto.getSkillLevel());
        employeeSkill.setExperienceYears(dto.getExperienceYears());

        return employeeSkill;
    }
}