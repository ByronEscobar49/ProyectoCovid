package org.progra3.covidtracker.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.progra3.covidtracker.model.Province;


import java.util.List;

@Data
public class ProvinceDTO {


    private String id;
    private String name;
    private RegionDTO region;
    @Setter
    @Getter
    private List<Province> data;
}
