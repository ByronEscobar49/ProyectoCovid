package org.progra3.covidtracker.model.dto;

import lombok.Data;

@Data
public class ReportDTO {

    private String region;
    private String province;
    private String date;
    private int confirmed;
    private int deaths;
    private int recovered;


}
