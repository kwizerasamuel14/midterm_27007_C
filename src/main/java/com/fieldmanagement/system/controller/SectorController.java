package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.entity.Sector;
import com.fieldmanagement.system.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {
    @Autowired
    private SectorService sectorService;

    @GetMapping
    public List<Sector> getAllSectors() {
        return sectorService.getAllSectors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sector> getSectorById(@PathVariable Long id) {
        Sector sector = sectorService.getSectorById(id);
        return sector != null ? ResponseEntity.ok(sector) : ResponseEntity.notFound().build();
    }

    @GetMapping("/district/{districtId}")
    public List<Sector> getSectorsByDistrict(@PathVariable Long districtId) {
        return sectorService.getSectorsByDistrictId(districtId);
    }

    @PostMapping
    public Sector createSector(@RequestBody Sector sector) {
        return sectorService.saveSector(sector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sector> updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        sector.setId(id);
        return ResponseEntity.ok(sectorService.saveSector(sector));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
        return ResponseEntity.noContent().build();
    }
}
