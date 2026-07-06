package com.erasm.service;

import java.util.List;

import com.erasm.dto.EmployeeSkillMatchDTO;

public interface SkillMatchingService {

    List<EmployeeSkillMatchDTO> getEmployeesBySkill(String skillName);

}