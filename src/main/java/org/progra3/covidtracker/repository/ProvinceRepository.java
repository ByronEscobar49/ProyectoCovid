package org.progra3.covidtracker.repository;

import org.progra3.covidtracker.model.Province;
import org.progra3.covidtracker.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Province findByNameAndRegion(String name, Region region);
    // Additional method to search by similar name
    @Query("SELECT p FROM Province p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Province> findByNameContainingIgnoreCase(@Param("name") String name);
}
