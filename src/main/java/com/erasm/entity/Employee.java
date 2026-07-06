package com.erasm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity


@Table(name="employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
private Long employeeId;
	
	
	@Column(nullable=false,unique=true)
	private String employeeCode;
	private String firstName;
	private String lastName;
	private String designation;
	private Double experience;
	
	
	@OneToOne
	 @JoinColumn(name = "user_id")
    private User user;

    public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Double getExperience() {
		return experience;
	}

	public void setExperience(Double experience) {
		this.experience = experience;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Employee() {
    }

	public Employee(Long employeeId, String employeeCode, String firstName, String lastName, String designation,
			Double experience, User user) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.designation = designation;
		this.experience = experience;
		this.user = user;
	}
	
	
}
