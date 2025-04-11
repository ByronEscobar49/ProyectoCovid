package org.progra3.covidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String regionIso;

    public Province(String code, String name, String regionIso) {
        this.code = code;
        this.name = name;
        this.regionIso = regionIso;
    }

}