package com.fieldmanagement.system.service;

import com.fieldmanagement.system.dto.ProvinceDTO;
import com.fieldmanagement.system.entity.Province;
import com.fieldmanagement.system.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProvinceService {
    
    @Autowired
    private ProvinceRepository provinceRepository;
    
    public Province saveProvince(ProvinceDTO dto) {
        if (provinceRepository.existsByCode(dto.getCode())) {
            throw new RuntimeException("Province code already exists");
        }
        
        Province province = new Province();
        province.setCode(dto.getCode());
        province.setName(dto.getName());
        
        return provinceRepository.save(province);
    }
    
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }
    
    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Province not found"));
    }
}
