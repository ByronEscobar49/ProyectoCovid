package org.progra3.covidtracker.service;

import lombok.extern.slf4j.Slf4j;
import org.progra3.covidtracker.model.Province;
import org.progra3.covidtracker.model.Region;
import org.progra3.covidtracker.model.Report;
import org.progra3.covidtracker.model.dto.RegionDTO;
import org.progra3.covidtracker.model.dto.ProvinceDTO;
import org.progra3.covidtracker.model.dto.ReportDTO;
import org.progra3.covidtracker.repository.ProvinceRepository;
import org.progra3.covidtracker.repository.RegionRepository;
import org.progra3.covidtracker.repository.ReportRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;

import java.util.List;

@Slf4j
@Service
public class CovidApiService {


    private static final Logger logger = LoggerFactory.getLogger(CovidApiService.class);

    private final RestTemplate restTemplate;
    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final ReportRepository reportRepository;

    private final String BASE_URL = "https://covid-19-statistics.p.rapidapi.com";
    private final String API_KEY = "2505eda46amshc60713983b5e807p1da25ajsn36febcbf4a71";
    private final String API_HOST = "covid-19-statistics.p.rapidapi.com";

    public CovidApiService(RestTemplate restTemplate,
                           RegionRepository regionRepository,
                           ProvinceRepository provinceRepository,
                           ReportRepository reportRepository) {
        this.restTemplate = restTemplate;
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.reportRepository = reportRepository;
    }

    public void fetchAndStoreData() {
        logger.info("⏳ Iniciando proceso de consumo de datos de COVID...");

        String iso = "GTM";
        String date = "2022-04-16";

        try {
            RegionDTO regionDTO = fetchRegions();
            List<Region> regions = regionDTO.getData();
            regionRepository.saveAll(regions);
            logger.info("✅ Regiones guardadas: {}", regions.size());

            ProvinceDTO provinceDTO = fetchProvinces(iso);
            List<Province> provinces = provinceDTO.getData();
            provinceRepository.saveAll(provinces);
            logger.info("✅ Provincias guardadas: {}", provinces.size());

            ReportDTO reportDTO = fetchReports(iso, date);
            List<Report> reports = reportDTO.getData();
            reportRepository.saveAll(reports);
            logger.info("✅ Reportes guardados: {}", reports.size());

            logger.info("🎉 Proceso finalizado exitosamente.");

        } catch (Exception e) {
            logger.error("❌ Error al consumir la API: ", e);
        }
    }

    private RegionDTO fetchRegions() {
        HttpHeaders headers = createHeaders();
        String url = BASE_URL + "/regions";

        ResponseEntity<RegionDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                RegionDTO.class
        );
        return response.getBody();
    }

    private ProvinceDTO fetchProvinces(String iso) {
        HttpHeaders headers = createHeaders();
        String url = String.format("%s/provinces?iso=%s", BASE_URL, iso);

        ResponseEntity<ProvinceDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ProvinceDTO.class
        );
        return response.getBody();
    }

    private ReportDTO fetchReports(String iso, String date) {
        HttpHeaders headers = createHeaders();
        headers.set("X-RapidAPI-Region", "us-east-1");

        String url = String.format("%s/reports?iso=%s&date=%s", BASE_URL, iso, date);

        ResponseEntity<ReportDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ReportDTO.class
        );
        return response.getBody();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", API_HOST);
        return headers;
    }

    public void fetchData() {
    }
    @Autowired
    private RegionRepository RegionRepository;

    public void saveRegions(List<Region> regions) {
        RegionRepository.saveAll(regions);

    }

    }

