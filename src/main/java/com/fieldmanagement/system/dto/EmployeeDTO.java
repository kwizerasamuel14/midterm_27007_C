package com.fieldmanagement.system.dto;

import java.util.List;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String jobRole;
    private String department;
    private String status;
    private Long provinceId;
    private Long siteId;
    private List<Long> skillIds;
    
    public EmployeeDTO() {}
    
    public EmployeeDTO(String firstName, String lastName, String email, String phone, 
                       String jobRole, String department, String status, Long provinceId, 
                       Long siteId, List<Long> skillIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobRole = jobRole;
        this.department = department;
        this.status = status;
        this.provinceId = provinceId;
        this.siteId = siteId;
        this.skillIds = skillIds;
    }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getJobRole() { return jobRole; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Long getProvinceId() { return provinceId; }
    public void setProvinceId(Long provinceId) { this.provinceId = provinceId; }
    
    public Long getSiteId() { return siteId; }
    public void setSiteId(Long siteId) { this.siteId = siteId; }
    
    public List<Long> getSkillIds() { return skillIds; }
    public void setSkillIds(List<Long> skillIds) { this.skillIds = skillIds; }
}
