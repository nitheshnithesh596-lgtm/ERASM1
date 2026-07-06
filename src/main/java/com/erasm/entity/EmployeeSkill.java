package com.erasm.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "employee_skills")
public    class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long employeeSkillId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private   String skillLevel;

    private Integer  experienceYears;

    public EmployeeSkill()    {
    	
    	
    	
    	
    	
       }

    public EmployeeSkill(Long employeeSkillId,
            Employee employee,
            Skill    skill,
            String  skillLevel,
            Integer  experienceYears) {

        this.employeeSkillId=employeeSkillId;
        this.employee =employee;
        this.skill =skill;
        this.skillLevel=skillLevel;
        this.experienceYears =experienceYears;
    }

    public Long getEmployeeSkillId() {
        return employeeSkillId;
    }

    public void setEmployeeSkillId(Long employeeSkillId) {
        this.employeeSkillId =employeeSkillId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee =employee;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill= skill;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel =skillLevel;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears =experienceYears;
    }
}
