package org.progra3.covidtracker.repository;

import org.progra3.covidtracker.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
