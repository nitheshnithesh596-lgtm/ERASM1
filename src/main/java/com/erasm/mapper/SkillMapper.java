package com.erasm.mapper;

import com.erasm.dto.SkillDTO;
import com.erasm.entity.Skill;

public class SkillMapper {

    public static SkillDTO toDTO(Skill skill) {

        if (skill == null) {
            return null;
        }

        SkillDTO dto = new SkillDTO();

        dto.setSkillId(skill.getSkillId());
        dto.setSkillName(skill.getSkillName());

        return dto;
    }

    public static Skill toEntity(SkillDTO dto) {

        if (dto == null) {
            return null;
        }

        Skill skill = new Skill();

        skill.setSkillId(dto.getSkillId());
        skill.setSkillName(dto.getSkillName());

        return skill;
    }
}