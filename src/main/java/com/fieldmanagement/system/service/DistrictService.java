package com.fieldmanagement.system.service;

import com.fieldmanagement.system.entity.District;
import com.fieldmanagement.system.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    public District getDistrictById(Long id) {
        return districtRepository.findById(id).orElse(null);
    }

    public List<District> getDistrictsByProvinceId(Long provinceId) {
        return districtRepository.findByProvinceId(provinceId);
    }

    public District saveDistrict(District district) {
        return districtRepository.save(district);
    }

    public void deleteDistrict(Long id) {
        districtRepository.deleteById(id);
    }
}
