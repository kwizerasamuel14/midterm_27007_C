package com.fieldmanagement.system.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Sector> sectors;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }

    public List<Sector> getSectors() { return sectors; }
    public void setSectors(List<Sector> sectors) { this.sectors = sectors; }
}
