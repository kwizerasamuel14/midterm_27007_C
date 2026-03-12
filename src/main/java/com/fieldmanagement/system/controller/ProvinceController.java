package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.dto.ProvinceDTO;
import com.fieldmanagement.system.entity.Province;
import com.fieldmanagement.system.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {
    
    @Autowired
    private ProvinceService provinceService;
    
    @PostMapping
    public ResponseEntity<Province> createProvince(@RequestBody ProvinceDTO dto) {
        Province province = provinceService.saveProvince(dto);
        return new ResponseEntity<>(province, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces() {
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        return ResponseEntity.ok(provinceService.getProvinceById(id));
    }
}
