package com.erasm.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "certifications")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    private String certificationName;

    private String issuedBy;

    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Certification() {
    }

    public Certification(Long certificationId, String certificationName,
            String issuedBy, LocalDate issueDate, Employee employee) {

        this.certificationId = certificationId;
        this.certificationName = certificationName;
        this.issuedBy = issuedBy;
        this.issueDate = issueDate;
        this.employee = employee;
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}