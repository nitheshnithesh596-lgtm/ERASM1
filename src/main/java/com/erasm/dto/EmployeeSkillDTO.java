package com.erasm.dto;

public class EmployeeSkillDTO {

    private Long employeeSkillId;
    private Long employeeId;
    private Long skillId;
    private String skillLevel;
    private Integer experienceYears;

    public EmployeeSkillDTO() {
    }

    public EmployeeSkillDTO(Long employeeSkillId, Long employeeId,
            Long skillId, String skillLevel, Integer experienceYears) {

        this.employeeSkillId = employeeSkillId;
        this.employeeId = employeeId;
        this.skillId = skillId;
        this.skillLevel = skillLevel;
        this.experienceYears = experienceYears;
    }

    public Long getEmployeeSkillId() {
        return employeeSkillId;
    }

    public void setEmployeeSkillId(Long employeeSkillId) {
        this.employeeSkillId = employeeSkillId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}