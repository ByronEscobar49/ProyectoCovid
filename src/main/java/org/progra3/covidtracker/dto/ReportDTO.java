package org.progra3.covidtracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.progra3.covidtracker.model.Report;

import java.util.List;

@Data
public class ReportDTO {

    private String region;
    private String province;
    private String date;
    private int confirmed;
    private int deaths;
    private int recovered;
    @Setter
    @Getter
    private List<Report> data;

}
