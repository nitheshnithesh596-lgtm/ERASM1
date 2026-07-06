package com.erasm.mapper;

import com.erasm.dto.CertificationDTO;
import com.erasm.entity.Certification;

public class CertificationMapper {

   
    public static CertificationDTO toDTO(Certification certification) {

        if (certification == null) {
            return null;
        }

        CertificationDTO dto = new CertificationDTO();

        dto.setCertificationId(certification.getCertificationId());
        dto.setCertificationName(certification.getCertificationName());
        dto.setIssuedBy(certification.getIssuedBy());
        dto.setIssueDate(certification.getIssueDate());

        if (certification.getEmployee() != null) {
            dto.setEmployeeId(certification.getEmployee().getEmployeeId());
        }

        return dto;
    }

  
    public static Certification toEntity(CertificationDTO dto) {

        if (dto == null) {
            return null;
        }

        Certification certification = new Certification();

        certification.setCertificationId(dto.getCertificationId());
        certification.setCertificationName(dto.getCertificationName());
        certification.setIssuedBy(dto.getIssuedBy());
        certification.setIssueDate(dto.getIssueDate());

      

        return certification;
    }
}