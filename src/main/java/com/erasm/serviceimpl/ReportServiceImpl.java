package com.erasm.serviceimpl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.ReportDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.EmployeeSkill;
import com.erasm.entity.Allocation;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.EmployeeSkillRepository;
import com.erasm.repository.AllocationRepository;
import com.erasm.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    // ---------------- SKILL REPORT ----------------
    @Override
    public ReportDTO getSkillReport() {

        List<EmployeeSkill> allEmployeeSkills =
                employeeSkillRepository.findAll();

        Map<String, Long> skillCount = allEmployeeSkills.stream()
                .collect(Collectors.groupingBy(
                        es -> es.getSkill().getSkillName(),
                        Collectors.counting()
                ));

        return new ReportDTO(Map.of("skillReport", skillCount));
    }

    // ---------------- UTILIZATION REPORT ----------------
    @Override
    public ReportDTO getUtilizationReport() {

        List<Employee> employees = employeeRepository.findAll();

        Map<String, Object> result = new HashMap<>();

        for (Employee emp : employees) {

            List<Allocation> allocations =
                    allocationRepository.findByEmployee(emp);

            int total = allocations.stream()
                    .mapToInt(Allocation::getAllocationPercentage)
                    .sum();

            int billable = Math.min(total, 100);
            int bench = Math.max(0, 100 - total);

            Map<String, Integer> stats = new HashMap<>();
            stats.put("billable%", billable);
            stats.put("bench%", bench);

            result.put(emp.getFirstName(), stats);
        }

        return new ReportDTO(result);
    }

    // ---------------- PROJECT REPORT ----------------
    @Override
    public ReportDTO getProjectAllocationReport() {

        List<Allocation> allocations = allocationRepository.findAll();

        Map<String, Long> projectData = allocations.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getProject().getProjectName(),
                        Collectors.counting()
                ));

        return new ReportDTO(Map.of("projectReport", projectData));
    }
}