package org.progra3.covidtracker.service;

import org.progra3.covidtracker.model.Province;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    public void saveAll(List<Province> provinces) {

        System.out.println("Saving provinces: " + provinces);
    }

}
