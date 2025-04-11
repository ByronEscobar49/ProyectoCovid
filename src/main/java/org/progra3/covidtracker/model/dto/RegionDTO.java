package org.progra3.covidtracker.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.progra3.covidtracker.model.Region;

import java.util.List;

@Data
public class RegionDTO {
    private String iso;
    private String name;
    @Setter
    @Getter
    private List<Region> data;

}
