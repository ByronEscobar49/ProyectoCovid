package org.progra3.covidtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "regions")
public class Region {
    @Id
    private String iso;
    private String name;
}

