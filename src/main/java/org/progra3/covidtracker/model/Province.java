package org.progra3.covidtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Entity
@Data
public class Province {
    @Id
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "region_iso")
    private Region region;
    // Getters y setters
}