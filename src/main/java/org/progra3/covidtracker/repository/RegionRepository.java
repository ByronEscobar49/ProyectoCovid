package org.progra3.covidtracker.repository;

import org.progra3.covidtracker.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RegionRepository extends JpaRepository<Region, Integer> {
}
