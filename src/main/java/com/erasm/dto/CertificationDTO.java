package com.erasm.dto;

import java.time.LocalDate;

public class CertificationDTO {

    private Long certificationId;
    private String certificationName;
    private String issuedBy;
    private LocalDate issueDate;
    private Long employeeId;

    public CertificationDTO() {
    }

    public CertificationDTO(Long certificationId, String certificationName,
            String issuedBy, LocalDate issueDate, Long employeeId) {

        this.certificationId = certificationId;
        this.certificationName = certificationName;
        this.issuedBy = issuedBy;
        this.issueDate = issueDate;
        this.employeeId = employeeId;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}