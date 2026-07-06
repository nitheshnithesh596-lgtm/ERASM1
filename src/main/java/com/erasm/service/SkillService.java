package com.erasm.service;

import org.springframework.data.domain.Page;

import com.erasm.dto.SkillDTO;

public interface SkillService {

    SkillDTO saveSkill(SkillDTO skillDTO);

    SkillDTO getSkillById(Long skillId);

    Page<SkillDTO> getAllSkills(
            int page,
            int size,
            String sortBy,
            String direction);

    SkillDTO updateSkill(Long skillId, SkillDTO skillDTO);

    void deleteSkill(Long skillId);
}