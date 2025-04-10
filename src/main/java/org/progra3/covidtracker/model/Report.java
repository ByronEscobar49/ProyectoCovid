package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.progra3.covidtracker.model.dto.ReportDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
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