package com.fieldmanagement.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "provinces")
public class Province {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 10)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"province"})
    private List<Employee> employees;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"province"})
    private List<District> districts;

    public List<District> getDistricts() { return districts; }
    public void setDistricts(List<District> districts) { this.districts = districts; }
    
    public Province() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}
