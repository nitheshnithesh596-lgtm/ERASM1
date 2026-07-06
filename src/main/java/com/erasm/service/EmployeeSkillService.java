package com.erasm.service;

import java.util.List;
import com.erasm.dto.EmployeeSkillDTO;

public interface EmployeeSkillService {

    EmployeeSkillDTO saveEmployeeSkill(EmployeeSkillDTO employeeSkillDTO);

    EmployeeSkillDTO getEmployeeSkillById(Long employeeSkillId);

    List<EmployeeSkillDTO> getAllEmployeeSkills();

    EmployeeSkillDTO updateEmployeeSkill(Long employeeSkillId, EmployeeSkillDTO employeeSkillDTO);

    void deleteEmployeeSkill(Long employeeSkillId);
}