package com.fieldmanagement.system.service;

import com.fieldmanagement.system.entity.Skill;
import com.fieldmanagement.system.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }
    
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
