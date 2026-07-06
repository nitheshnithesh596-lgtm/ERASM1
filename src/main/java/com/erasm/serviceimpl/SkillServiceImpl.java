package com.erasm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.erasm.dto.SkillDTO;
import com.erasm.entity.Skill;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.SkillMapper;
import com.erasm.repository.SkillRepository;
import com.erasm.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public SkillDTO saveSkill(SkillDTO skillDTO) {

        if (skillRepository.findBySkillName(skillDTO.getSkillName()).isPresent()) {
            throw new RuntimeException("Skill already exists.");
        }

        Skill skill = SkillMapper.toEntity(skillDTO);

        Skill savedSkill = skillRepository.save(skill);

        return SkillMapper.toDTO(savedSkill);
    }

    @Override
    public SkillDTO getSkillById(Long skillId) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with ID : " + skillId));

        return SkillMapper.toDTO(skill);
    }

    @Override
    public Page<SkillDTO> getAllSkills(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort);

        return skillRepository.findAll(pageable)
                .map(SkillMapper::toDTO);
    }

    @Override
    public SkillDTO updateSkill(Long skillId, SkillDTO skillDTO) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with ID : " + skillId));

        skill.setSkillName(skillDTO.getSkillName());

        Skill updatedSkill = skillRepository.save(skill);

        return SkillMapper.toDTO(updatedSkill);
    }

    @Override
    public void deleteSkill(Long skillId) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with ID : " + skillId));

        skillRepository.delete(skill);
    }
}