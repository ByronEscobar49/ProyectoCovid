package org.progra3.covidtracker.repository;

import org.progra3.covidtracker.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByIso(String iso);
}