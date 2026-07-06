package com.erasm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.erasm.dto.EmployeeDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.User;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.EmployeeMapper;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.UserRepository;
import com.erasm.service.AuditService;
import com.erasm.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        if (employeeRepository.existsByEmployeeCode(employeeDTO.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists.");
        }

        Employee employee = EmployeeMapper.toEntity(employeeDTO);

        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID : " + employeeDTO.getUserId()));

        employee.setUser(user);

        Employee savedEmployee = employeeRepository.save(employee);

        auditService.logAction(
                "CREATE_EMPLOYEE",
                "ADMIN",
                "Employee created successfully. Employee Code : "
                        + savedEmployee.getEmployeeCode());

        return EmployeeMapper.toDTO(savedEmployee);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(EmployeeMapper::toDTO);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID : " + employeeId));

        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long employeeId,
                                      EmployeeDTO employeeDTO) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID : " + employeeId));

        if (!employee.getEmployeeCode().equals(employeeDTO.getEmployeeCode())
                && employeeRepository.existsByEmployeeCode(employeeDTO.getEmployeeCode())) {

            throw new RuntimeException("Employee code already exists.");
        }

        employee.setEmployeeCode(employeeDTO.getEmployeeCode());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setExperience(employeeDTO.getExperience());

        if (employeeDTO.getUserId() != null) {

            User user = userRepository.findById(employeeDTO.getUserId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with ID : "
                                            + employeeDTO.getUserId()));

            employee.setUser(user);
        }

        Employee updatedEmployee = employeeRepository.save(employee);

        auditService.logAction(
                "UPDATE_EMPLOYEE",
                "ADMIN",
                "Employee updated successfully. Employee Code : "
                        + updatedEmployee.getEmployeeCode());

        return EmployeeMapper.toDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID : " + employeeId));

        auditService.logAction(
                "DELETE_EMPLOYEE",
                "ADMIN",
                "Employee deleted successfully. Employee Code : "
                        + employee.getEmployeeCode());

        employeeRepository.delete(employee);
    }

}