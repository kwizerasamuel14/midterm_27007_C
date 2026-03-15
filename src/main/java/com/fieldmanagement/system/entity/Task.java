package com.fieldmanagement.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({"attendances", "profile", "skills"})
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    @JsonIgnoreProperties({"tasks", "employees"})
    private Site site;
    
    private LocalDate deadline;
    
    @Column(nullable = false)
    private String status;
    
    public Task() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
