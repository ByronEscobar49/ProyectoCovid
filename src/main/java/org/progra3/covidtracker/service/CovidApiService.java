package org.progra3.covidtracker.service;

import lombok.RequiredArgsConstructor;
import org.progra3.covidtracker.model.Province;
import org.progra3.covidtracker.model.Region;
import org.progra3.covidtracker.model.Report;
import org.progra3.covidtracker.model.dto.ProvinceDTO;
import org.progra3.covidtracker.model.dto.RegionDTO;
import org.progra3.covidtracker.model.dto.ReportDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CovidApiService {
    private final RestTemplate restTemplate;

    private final String BASE_URL = "https://covid-19-statistics.p.rapidapi.com";
    private final String API_KEY = "2505eda46amshc60713983b5e807p1da25ajsn36febcbf4a71";
    private final String API_HOST = "covid-19-statistics.p.rapidapi.com";

    public RegionDTO[] fetchRegions() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", API_HOST);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Region> response = restTemplate.exchange(
                BASE_URL + "/regions",
                HttpMethod.GET,
                request,
                Region.class
        );
        return response.getBody() != null ? response.getBody().getData() : new RegionDTO[0];
    }

    public ProvinceDTO[] fetchProvinces(String iso) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", API_HOST);

        Map<String, String> payload = Map.of("iso", iso);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Province> response = restTemplate.exchange(
                BASE_URL + "/provinces",
                HttpMethod.POST,
                request,
                Province.class
        );
        return response.getBody() != null ? response.getBody().getData() : new ProvinceDTO[0];
    }

    public ReportDTO[] fetchReports(String iso, String date) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", API_HOST);
        headers.set("x-rapidapi-region", "AWS - us-east-1");
        headers.set("x-rapidapi-request-id", UUID.randomUUID().toString());
        headers.set("x-rapidapi-version", "1.2.8");

        HttpEntity<Void> request = new HttpEntity<>(headers);

        String url = String.format(BASE_URL + "/reports?iso=%s&date=%s", iso, date);

        ResponseEntity<Report> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Report.class
        );
        return response.getBody() != null ? response.getBody().getData() : new ReportDTO[0];
    }
}
