package org.progra3.covidtracker.repository;

import org.progra3.covidtracker.model.Province;
import org.progra3.covidtracker.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByProvinceAndDate(Province province, LocalDate date); // Verificación única
}
