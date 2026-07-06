package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.CertificationDTO;
import com.erasm.entity.Certification;
import com.erasm.entity.Employee;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.CertificationMapper;
import com.erasm.repository.CertificationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.service.AuditService;
import com.erasm.service.CertificationService;

@Service
public class CertificationServiceImpl implements CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public CertificationDTO saveCertification(CertificationDTO certificationDTO) {

        Certification certification =
                CertificationMapper.toEntity(certificationDTO);

        Employee employee = employeeRepository.findById(
                certificationDTO.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : "
                                        + certificationDTO.getEmployeeId()));

        certification.setEmployee(employee);

        Certification savedCertification =
                certificationRepository.save(certification);

        auditService.logAction(
                "CREATE_CERTIFICATION",
                "ADMIN",
                "Created certification "
                        + savedCertification.getCertificationName()
                        + " for employee "
                        + employee.getEmployeeCode());

        return CertificationMapper.toDTO(savedCertification);
    }

    @Override
    public List<CertificationDTO> getAllCertifications() {

        return certificationRepository.findAll()
                .stream()
                .map(CertificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CertificationDTO getCertificationById(Long certificationId) {

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certification not found with id : "
                                                + certificationId));

        return CertificationMapper.toDTO(certification);
    }    @Override
    public CertificationDTO updateCertification(
            Long certificationId,
            CertificationDTO certificationDTO) {

        Certification certification = certificationRepository.findById(certificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Certification not found with id : "
                                        + certificationId));

        Employee employee = employeeRepository.findById(
                certificationDTO.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : "
                                        + certificationDTO.getEmployeeId()));

        certification.setCertificationName(
                certificationDTO.getCertificationName());

        certification.setIssuedBy(
                certificationDTO.getIssuedBy());

        certification.setIssueDate(
                certificationDTO.getIssueDate());

        certification.setEmployee(employee);

        Certification updatedCertification =
                certificationRepository.save(certification);

        auditService.logAction(
                "UPDATE_CERTIFICATION",
                "ADMIN",
                "Updated certification "
                        + updatedCertification.getCertificationName()
                        + " for employee "
                        + employee.getEmployeeCode());

        return CertificationMapper.toDTO(updatedCertification);
    }

    @Override
    public void deleteCertification(Long certificationId) {

        Certification certification = certificationRepository.findById(certificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Certification not found with id : "
                                        + certificationId));

        auditService.logAction(
                "DELETE_CERTIFICATION",
                "ADMIN",
                "Deleted certification : "
                        + certification.getCertificationName());

        certificationRepository.delete(certification);
    }
}