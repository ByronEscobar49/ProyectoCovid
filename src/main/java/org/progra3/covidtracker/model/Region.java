package org.progra3.covidtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.progra3.covidtracker.model.dto.RegionDTO;

@Data
@Entity
@Table(name = "regions")
public class Region {
    @Transient
    private RegionDTO[] data;
    @Id
    private String iso;
    private String name;
}

