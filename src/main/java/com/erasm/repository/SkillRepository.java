package com.erasm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erasm.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findBySkillName(String skillName);

    List<Skill> findBySkillNameContainingIgnoreCase(String skillName);

}