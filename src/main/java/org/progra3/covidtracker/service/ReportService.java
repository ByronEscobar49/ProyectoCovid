package org.progra3.covidtracker.service;

import org.progra3.covidtracker.model.Report;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportService {
    public void saveAll(List<Report> reports) {
        System.out.println("Saving reports: " + reports);
    }
}
