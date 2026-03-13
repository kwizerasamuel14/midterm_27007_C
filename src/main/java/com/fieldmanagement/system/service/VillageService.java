package com.fieldmanagement.system.service;

import com.fieldmanagement.system.entity.Village;
import com.fieldmanagement.system.repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VillageService {
    @Autowired
    private VillageRepository villageRepository;

    public List<Village> getAllVillages() {
        return villageRepository.findAll();
    }

    public Village getVillageById(Long id) {
        return villageRepository.findById(id).orElse(null);
    }

    public List<Village> getVillagesByCellId(Long cellId) {
        return villageRepository.findByCellId(cellId);
    }

    public Village saveVillage(Village village) {
        return villageRepository.save(village);
    }

    public void deleteVillage(Long id) {
        villageRepository.deleteById(id);
    }
}
