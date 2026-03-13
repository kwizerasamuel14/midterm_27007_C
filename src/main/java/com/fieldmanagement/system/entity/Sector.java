package com.fieldmanagement.system.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sectors")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<Cell> cells;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }

    public List<Cell> getCells() { return cells; }
    public void setCells(List<Cell> cells) { this.cells = cells; }
}
