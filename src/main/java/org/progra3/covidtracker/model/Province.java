package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.progra3.covidtracker.model.dto.ProvinceDTO;

@Entity
@Data
public class Province {
    @Transient
    private ProvinceDTO[] data;
    @Id
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "region_iso")
    private Region region;
    // Getters y setters
}