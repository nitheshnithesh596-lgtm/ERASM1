package com.erasm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erasm.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByProjectName(String projectName);

    boolean existsByProjectName(String projectName);

    List<Project> findByStatus(String status);

    List<Project> findByProjectNameContainingIgnoreCase(String projectName);

}