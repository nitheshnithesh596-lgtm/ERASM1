package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.CertificationDTO;
import com.erasm.dto.EmployeeDTO;
import com.erasm.dto.ProjectDTO;
import com.erasm.dto.SkillDTO;
import com.erasm.mapper.CertificationMapper;
import com.erasm.mapper.EmployeeMapper;
import com.erasm.mapper.ProjectMapper;
import com.erasm.mapper.SkillMapper;
import com.erasm.repository.CertificationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;
import com.erasm.repository.SkillRepository;
import com.erasm.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Override
    public List<EmployeeDTO> searchEmployees(String keyword) {

        return employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> searchProjects(String keyword) {

        return projectRepository
                .findByProjectNameContainingIgnoreCase(keyword)
                .stream()
                .map(ProjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SkillDTO> searchSkills(String keyword) {

        return skillRepository
                .findBySkillNameContainingIgnoreCase(keyword)
                .stream()
                .map(SkillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CertificationDTO> searchCertifications(String keyword) {

        return certificationRepository
                .findByCertificationNameContainingIgnoreCase(keyword)
                .stream()
                .map(CertificationMapper::toDTO)
                .collect(Collectors.toList());
    }

}