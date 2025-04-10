package org.progra3.covidtracker.service;

import org.progra3.covidtracker.model.dto.RegionDTO;
import org.progra3.covidtracker.model.dto.ProvinceDTO;
import org.progra3.covidtracker.model.dto.ReportDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CovidApiService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://covid-19-statistics.p.rapidapi.com";
    private final String API_KEY = "tu-api-key-real"; // Reemplaza con tu key
    private final String API_HOST = "covid-19-statistics.p.rapidapi.com";

    public CovidApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RegionDTO fetchRegions() {
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

    public ProvinceDTO fetchProvinces(String iso) {
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

    public ReportDTO fetchReports(String iso, String date) {
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
}