package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.entity.Village;
import com.fieldmanagement.system.service.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/villages")
public class VillageController {
    @Autowired
    private VillageService villageService;

    @GetMapping
    public List<Village> getAllVillages() {
        return villageService.getAllVillages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Village> getVillageById(@PathVariable Long id) {
        Village village = villageService.getVillageById(id);
        return village != null ? ResponseEntity.ok(village) : ResponseEntity.notFound().build();
    }

    @GetMapping("/cell/{cellId}")
    public List<Village> getVillagesByCell(@PathVariable Long cellId) {
        return villageService.getVillagesByCellId(cellId);
    }

    @PostMapping
    public Village createVillage(@RequestBody Village village) {
        return villageService.saveVillage(village);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Village> updateVillage(@PathVariable Long id, @RequestBody Village village) {
        village.setId(id);
        return ResponseEntity.ok(villageService.saveVillage(village));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVillage(@PathVariable Long id) {
        villageService.deleteVillage(id);
        return ResponseEntity.noContent().build();
    }
}
