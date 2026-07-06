package com.erasm.service;

import java.util.List;
import com.erasm.dto.CertificationDTO;

public interface CertificationService {

    CertificationDTO saveCertification(CertificationDTO certificationDTO);

    CertificationDTO getCertificationById(Long certificationId);

    List<CertificationDTO> getAllCertifications();

    CertificationDTO updateCertification(Long certificationId, CertificationDTO certificationDTO);

    void deleteCertification(Long certificationId);
}