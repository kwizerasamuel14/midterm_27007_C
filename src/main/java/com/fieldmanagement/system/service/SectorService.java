package com.fieldmanagement.system.service;

import com.fieldmanagement.system.entity.Sector;
import com.fieldmanagement.system.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SectorService {
    @Autowired
    private SectorRepository sectorRepository;

    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    public Sector getSectorById(Long id) {
        return sectorRepository.findById(id).orElse(null);
    }

    public List<Sector> getSectorsByDistrictId(Long districtId) {
        return sectorRepository.findByDistrictId(districtId);
    }

    public Sector saveSector(Sector sector) {
        return sectorRepository.save(sector);
    }

    public void deleteSector(Long id) {
        sectorRepository.deleteById(id);
    }
}
