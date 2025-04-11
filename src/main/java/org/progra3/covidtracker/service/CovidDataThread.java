package org.progra3.covidtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CovidDataThread implements CommandLineRunner {
    @Autowired
    private CovidApiService covidApiService;

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(15000); // espera de 15 segundos
        covidApiService.fetchData(); // aquí se hace todo el proceso
    }
}
