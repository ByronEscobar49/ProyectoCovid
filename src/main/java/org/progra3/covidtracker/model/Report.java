package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regionIso;
    private String provinceCode;
    private LocalDate date;
    private Integer confirmed;
    private Integer deaths;
    private Integer recovered;
    private Integer active;
    private Double fatalityRate;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    public Report(String regionIso, String provinceCode, LocalDate date,
                  Integer confirmed, Integer deaths, Integer recovered,
                  Integer active, Double fatalityRate) {
        this.regionIso = regionIso;
        this.provinceCode = provinceCode;
        this.date = date;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.fatalityRate = fatalityRate;

    }
}