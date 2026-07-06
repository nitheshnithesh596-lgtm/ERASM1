package com.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erasm.entity.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    List<Certification> findByCertificationNameContainingIgnoreCase(String certificationName);

}