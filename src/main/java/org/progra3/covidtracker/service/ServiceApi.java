package org.progra3.covidtracker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.progra3.covidtracker.client.ApiClient;
import org.progra3.covidtracker.config.AppConfig;
import org.springframework.stereotype.Service;


@Service
public class ServiceApi {

    private static final Logger logger = LogManager.getLogger(ServiceApi.class);
    private final ApiClient apiClient;
    private final CovidApiService covidApiService;
    private final AppConfig appConfig;

    public ServiceApi(ApiClient apiClient, CovidApiService covidApiService, AppConfig appConfig) {
        this.apiClient = apiClient;
        this.covidApiService = covidApiService;
        this.appConfig = appConfig;
    }

    public void fetchCovidData() {
        logger.info("Fetching COVID-19 data...");

        String countryIso = appConfig.getCountryIso();
        String reportDate = appConfig.getReportDate();

        logger.info("countryIso desde AppConfig: '{}'", countryIso);
        logger.info("reportDate desde AppConfig: '{}'", reportDate);


        try {
            // 🔹 Obtener regiones
            String regions = apiClient.getRegions();
            logger.info("Regions: " + regions);
            covidApiService.saveRegions(regions); // ✅ Guardar en base de datos

            // 🔹 Provincias
            String provinces = apiClient.getProvinces(countryIso);
            logger.info("Provinces for {}: {}", countryIso, provinces);
            covidApiService.saveProvinces(provinces, countryIso); // ✅ Guardar en base de datos

            // 🔹 Reporte
            String report = apiClient.getReport(countryIso, reportDate);
            logger.info("Report for {} on {}: {}", countryIso, reportDate, report);
            covidApiService.saveReports(report); // ✅ Guardar en base de datos

        } catch (Exception e) {
            logger.error("❌ Error al consumir la API o guardar en la BD:");

        }
    }
}
