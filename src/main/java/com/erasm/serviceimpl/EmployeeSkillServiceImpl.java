package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.EmployeeSkillDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.EmployeeSkill;
import com.erasm.entity.Skill;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.EmployeeSkillMapper;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.EmployeeSkillRepository;
import com.erasm.repository.SkillRepository;
import com.erasm.service.AuditService;
import com.erasm.service.EmployeeSkillService;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public EmployeeSkillDTO saveEmployeeSkill(EmployeeSkillDTO employeeSkillDTO) {

        EmployeeSkill employeeSkill =
                EmployeeSkillMapper.toEntity(employeeSkillDTO);

        Employee employee = employeeRepository.findById(
                employeeSkillDTO.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : "
                                        + employeeSkillDTO.getEmployeeId()));

        Skill skill = skillRepository.findById(
                employeeSkillDTO.getSkillId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id : "
                                        + employeeSkillDTO.getSkillId()));

        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);

        EmployeeSkill savedEmployeeSkill =
                employeeSkillRepository.save(employeeSkill);

        auditService.logAction(
                "CREATE_EMPLOYEE_SKILL",
                "ADMIN",
                "Added skill "
                        + skill.getSkillName()
                        + " to employee "
                        + employee.getEmployeeCode());

        return EmployeeSkillMapper.toDTO(savedEmployeeSkill);
    }

    @Override
    public List<EmployeeSkillDTO> getAllEmployeeSkills() {

        return employeeSkillRepository.findAll()
                .stream()
                .map(EmployeeSkillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeSkillDTO getEmployeeSkillById(Long employeeSkillId) {

        EmployeeSkill employeeSkill =
                employeeSkillRepository.findById(employeeSkillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee Skill not found with id : "
                                                + employeeSkillId));

        return EmployeeSkillMapper.toDTO(employeeSkill);
    }
    @Override
    public EmployeeSkillDTO updateEmployeeSkill(
            Long employeeSkillId,
            EmployeeSkillDTO employeeSkillDTO) {

        EmployeeSkill employeeSkill = employeeSkillRepository.findById(employeeSkillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee Skill not found with id : "
                                        + employeeSkillId));

        Employee employee = employeeRepository.findById(
                employeeSkillDTO.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : "
                                        + employeeSkillDTO.getEmployeeId()));

        Skill skill = skillRepository.findById(
                employeeSkillDTO.getSkillId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id : "
                                        + employeeSkillDTO.getSkillId()));

        employeeSkill.setSkillLevel(
                employeeSkillDTO.getSkillLevel());

        employeeSkill.setExperienceYears(
                employeeSkillDTO.getExperienceYears());

        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);

        EmployeeSkill updatedEmployeeSkill =
                employeeSkillRepository.save(employeeSkill);

        auditService.logAction(
                "UPDATE_EMPLOYEE_SKILL",
                "ADMIN",
                "Updated skill "
                        + skill.getSkillName()
                        + " for employee "
                        + employee.getEmployeeCode());

        return EmployeeSkillMapper.toDTO(updatedEmployeeSkill);
    }

    @Override
    public void deleteEmployeeSkill(Long employeeSkillId) {

        EmployeeSkill employeeSkill = employeeSkillRepository.findById(employeeSkillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee Skill not found with id : "
                                        + employeeSkillId));

        auditService.logAction(
                "DELETE_EMPLOYEE_SKILL",
                "ADMIN",
                "Deleted skill "
                        + employeeSkill.getSkill().getSkillName()
                        + " from employee "
                        + employeeSkill.getEmployee().getEmployeeCode());

        employeeSkillRepository.delete(employeeSkill);
    }
}