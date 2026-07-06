package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.erasm.dto.EmployeeDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.User;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.UserRepository;
import com.erasm.serviceimpl.EmployeeServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testGetEmployeeById() {

        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setEmployeeCode("EMP001");
        employee.setFirstName("Nithesh");
        employee.setLastName("Kumar");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeDTO dto = employeeService.getEmployeeById(1L);

        assertNotNull(dto);
        assertEquals("Nithesh", dto.getFirstName());

        verify(employeeRepository).findById(1L);
    }

    @Test
    void testEmployeeNotFound() {

        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(99L);
        });

        verify(employeeRepository).findById(99L);
    }

    @Test
    void testDeleteEmployee() {

        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setEmployeeCode("EMP001");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);

        verify(auditService).logAction(
                anyString(),
                anyString(),
                anyString());
    }

    @Test
    void testDeleteEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(1L));
    }

    @Test
    void testSaveDuplicateEmployeeCode() {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setEmployeeCode("EMP001");

        when(employeeRepository.existsByEmployeeCode("EMP001"))
                .thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> employeeService.saveEmployee(dto));
    }
}