package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.progra3.covidtracker.dto.RegionDTO;

import java.util.List;

@Data
@Entity
@Table(name = "regions")
public class Region {
    @Transient
    private RegionDTO[] data;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String iso;
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Province> provinces;

}

