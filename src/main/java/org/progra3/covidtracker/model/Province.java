package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.progra3.covidtracker.model.dto.ProvinceDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Province {
    @Id



    private String code;
    private String name;
    private String regionIso;

    public Province(String code, String name, String regionIso) {
        this.code = code;
        this.name = name;
        this.regionIso = regionIso;
    }

}