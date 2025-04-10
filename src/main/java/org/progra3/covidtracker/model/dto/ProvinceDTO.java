package org.progra3.covidtracker.model.dto;

import lombok.Data;

@Data
public class ProvinceDTO {


    private String id;
    private String name;
    private RegionDTO region;

}
