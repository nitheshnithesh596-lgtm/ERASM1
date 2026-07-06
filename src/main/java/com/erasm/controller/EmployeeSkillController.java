package com.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.EmployeeSkillDTO;
import com.erasm.service.EmployeeSkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee-skills")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;

    @PostMapping
    public ResponseEntity<EmployeeSkillDTO> saveEmployeeSkill(@Valid @RequestBody EmployeeSkillDTO employeeSkillDTO) {

        EmployeeSkillDTO savedEmployeeSkill = employeeSkillService.saveEmployeeSkill(employeeSkillDTO);

        return new ResponseEntity<>(savedEmployeeSkill, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeSkillDTO>> getAllEmployeeSkills() {

        List<EmployeeSkillDTO> employeeSkills = employeeSkillService.getAllEmployeeSkills();

        return ResponseEntity.ok(employeeSkills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeSkillDTO> getEmployeeSkillById(@PathVariable Long id) {

        EmployeeSkillDTO employeeSkill = employeeSkillService.getEmployeeSkillById(id);

        return ResponseEntity.ok(employeeSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeSkillDTO> updateEmployeeSkill(@PathVariable Long id,
                                                                @RequestBody EmployeeSkillDTO employeeSkillDTO) {

        EmployeeSkillDTO updatedEmployeeSkill = employeeSkillService.updateEmployeeSkill(id, employeeSkillDTO);

        return ResponseEntity.ok(updatedEmployeeSkill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeSkill(@PathVariable Long id) {

        employeeSkillService.deleteEmployeeSkill(id);

        return ResponseEntity.ok("Employee Skill deleted successfully.");
    }

}