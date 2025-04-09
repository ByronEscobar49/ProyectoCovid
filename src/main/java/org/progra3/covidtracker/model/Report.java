package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private int confirmed;
    private int deaths;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    // Getters y Setters
}