package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.entity.District;
import com.fieldmanagement.system.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {
    @Autowired
    private DistrictService districtService;

    @GetMapping
    public List<District> getAllDistricts() {
        return districtService.getAllDistricts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id) {
        District district = districtService.getDistrictById(id);
        return district != null ? ResponseEntity.ok(district) : ResponseEntity.notFound().build();
    }

    @GetMapping("/province/{provinceId}")
    public List<District> getDistrictsByProvince(@PathVariable Long provinceId) {
        return districtService.getDistrictsByProvinceId(provinceId);
    }

    @PostMapping
    public District createDistrict(@RequestBody District district) {
        return districtService.saveDistrict(district);
    }

    @PutMapping("/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable Long id, @RequestBody District district) {
        district.setId(id);
        return ResponseEntity.ok(districtService.saveDistrict(district));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }
}
