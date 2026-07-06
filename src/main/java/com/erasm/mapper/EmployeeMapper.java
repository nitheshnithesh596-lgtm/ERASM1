package com.erasm.mapper;

import com.erasm.dto.EmployeeDTO;
import com.erasm.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {

        if (employee == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();

        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeCode(employee.getEmployeeCode());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDesignation(employee.getDesignation());
        dto.setExperience(employee.getExperience());

        if (employee.getUser() != null) {
            dto.setUserId(employee.getUser().getUserId());
        }

        return dto;
    }

    public static Employee toEntity(EmployeeDTO dto) {

        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();

        employee.setEmployeeId(dto.getEmployeeId());
        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDesignation(dto.getDesignation());
        employee.setExperience(dto.getExperience());

        return employee;
    }
}