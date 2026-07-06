package com.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    List<Allocation> findByEmployee(Employee employee);

    List<Allocation> findByEmployeeEmployeeId(Long employeeId);

}