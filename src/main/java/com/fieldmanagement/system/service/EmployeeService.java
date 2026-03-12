package com.fieldmanagement.system.service;

import com.fieldmanagement.system.dto.EmployeeDTO;
import com.fieldmanagement.system.entity.Employee;
import com.fieldmanagement.system.entity.Province;
import com.fieldmanagement.system.entity.Site;
import com.fieldmanagement.system.entity.Skill;
import com.fieldmanagement.system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ProvinceRepository provinceRepository;
    
    @Autowired
    private SiteRepository siteRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    public Employee saveEmployee(EmployeeDTO dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        Province province = provinceRepository.findById(dto.getProvinceId())
            .orElseThrow(() -> new RuntimeException("Province not found"));
        
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setJobRole(dto.getJobRole());
        employee.setDepartment(dto.getDepartment());
        employee.setStatus(dto.getStatus());
        employee.setProvince(province);
        
        if (dto.getSiteId() != null) {
            Site site = siteRepository.findById(dto.getSiteId())
                .orElseThrow(() -> new RuntimeException("Site not found"));
            employee.setSite(site);
        }
        
        if (dto.getSkillIds() != null && !dto.getSkillIds().isEmpty()) {
            List<Skill> skills = dto.getSkillIds().stream()
                .map(id -> skillRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Skill not found: " + id)))
                .collect(Collectors.toList());
            employee.setSkills(skills);
        }
        
        return employeeRepository.save(employee);
    }
    
    public Page<Employee> getAllEmployeesWithPaginationAndSorting(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
    }
    
    public List<Employee> getEmployeesByProvinceCode(String provinceCode) {
        return employeeRepository.findByProvinceCode(provinceCode);
    }
    
    public List<Employee> getEmployeesByProvinceName(String provinceName) {
        return employeeRepository.findByProvinceName(provinceName);
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
