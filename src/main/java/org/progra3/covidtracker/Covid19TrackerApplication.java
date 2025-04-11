package org.progra3.covidtracker;

import jakarta.annotation.PostConstruct;
import org.progra3.covidtracker.config.StartupRunner;
import org.progra3.covidtracker.service.CovidApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StartupRunner.class)
public class Covid19TrackerApplication {
    private final CovidApiService apiService;

    // Constructor con inyección de dependencias
    @Autowired
    public Covid19TrackerApplication(CovidApiService apiService) {
        this.apiService = apiService;
    }

    // Método para obtener los datos de la API después de la inicialización de la aplicación
    @PostConstruct
    public void fetchData() {
        // Asegúrate de que fetchCovidData esté correctamente implementado en ApiService
        apiService.fetchCovidData();
    }

    public static void main(String[] args) {
        SpringApplication.run(Covid19TrackerApplication.class, args);
    }
}
