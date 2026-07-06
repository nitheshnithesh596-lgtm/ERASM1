package com.erasm.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.repository.AllocationRepository;
import com.erasm.repository.CertificationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;
import com.erasm.repository.ResourceRequestRepository;
import com.erasm.repository.SkillRepository;
import com.erasm.service.AuditService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger =
            LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private ResourceRequestRepository resourceRequestRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public DashboardDTO getDashboard() {

        logger.info("Generating dashboard.");

        DashboardDTO dashboard = new DashboardDTO();

        dashboard.setTotalEmployees(employeeRepository.count());

        dashboard.setTotalProjects(projectRepository.count());

        dashboard.setActiveProjects(
                projectRepository.findByStatus("ACTIVE").size());

        dashboard.setCompletedProjects(
                projectRepository.findByStatus("COMPLETED").size());

        dashboard.setTotalSkills(skillRepository.count());

        dashboard.setTotalAllocations(allocationRepository.count());

        dashboard.setTotalCertifications(
                certificationRepository.count());

        dashboard.setPendingResourceRequests(
                resourceRequestRepository.findByStatus("PENDING").size());

        auditService.logAction(
                "VIEW_DASHBOARD",
                "ADMIN",
                "Dashboard viewed");

        logger.info("Dashboard generated successfully.");

        return dashboard;
    }}