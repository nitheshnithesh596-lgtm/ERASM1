package com.erasm.service;

import org.springframework.data.domain.Page;

import com.erasm.dto.EmployeeDTO;

public interface EmployeeService {

   
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

   
    EmployeeDTO getEmployeeById(Long employeeId);

    Page<EmployeeDTO> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction);

    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO);

    void deleteEmployee(Long employeeId);
}