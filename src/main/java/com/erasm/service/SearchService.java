package com.erasm.service;

import java.util.List;

import com.erasm.dto.CertificationDTO;
import com.erasm.dto.EmployeeDTO;
import com.erasm.dto.ProjectDTO;
import com.erasm.dto.SkillDTO;

public interface SearchService {

    List<EmployeeDTO> searchEmployees(String keyword);

    List<ProjectDTO> searchProjects(String keyword);

    List<SkillDTO> searchSkills(String keyword);

    List<CertificationDTO> searchCertifications(String keyword);

}