package org.progra3.covidtracker.repository;

import org.progra3.covidtracker.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
