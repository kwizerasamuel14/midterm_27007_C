package com.fieldmanagement.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cells")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    @JsonIgnoreProperties({"cells"})
    private Sector sector;

    @OneToMany(mappedBy = "cell", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"cell"})
    private List<Village> villages;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }

    public List<Village> getVillages() { return villages; }
    public void setVillages(List<Village> villages) { this.villages = villages; }
}
