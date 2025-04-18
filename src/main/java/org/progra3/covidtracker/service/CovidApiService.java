package org.progra3.covidtracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.progra3.covidtracker.model.Region;
import org.progra3.covidtracker.model.Province;
import org.progra3.covidtracker.model.Report;
import org.progra3.covidtracker.repository.RegionRepository;
import org.progra3.covidtracker.repository.ProvinceRepository;
import org.progra3.covidtracker.repository.ReportRepository;


import java.time.LocalDate;
import java.util.*;

@Service
public class CovidApiService {
    private static final Logger logger = LogManager.getLogger(CovidApiService.class);

    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final ReportRepository reportRepository;
    private final ObjectMapper objectMapper;

    private final Map<String, Region> isoRegionMap = new HashMap<>();
    private final Map<String, Province> nameProvinceMap = new HashMap<>();

    public CovidApiService(RegionRepository regionRepository,
                           ProvinceRepository provinceRepository,
                           ReportRepository reportRepository) {
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.reportRepository = reportRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void saveRegions(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json).get("data");
        List<Region> regionsToSave = new ArrayList<>();
        Set<String> newIsos = new HashSet<>();

        for (JsonNode node : root) {
            String iso = node.get("iso").asText();

            if (newIsos.contains(iso)) {
                continue;
            }

            List<Region> existingList = regionRepository.findByIso(iso);
            Region region;

            if (!existingList.isEmpty()) {
                region = existingList.get(0);
                region.setName(node.get("name").asText());
                regionRepository.save(region);
            } else {
                region = new Region();
                region.setIso(iso);
                region.setName(node.get("name").asText());
                regionsToSave.add(region);
                newIsos.add(iso);
            }

            isoRegionMap.put(region.getIso(), region);
        }

        if (!regionsToSave.isEmpty()) {
            regionRepository.saveAll(regionsToSave);
        }
    }

    public void saveProvinces(String json, String isoCode) throws Exception {
        Region region = isoRegionMap.get(isoCode);
        if (region == null) {
            List<Region> existingList = regionRepository.findByIso(isoCode);
            if (existingList.isEmpty()) {
                throw new Exception("Region not found for ISO code: " + isoCode);
            }
            region = existingList.get(0);
            isoRegionMap.put(isoCode, region);
        }

        JsonNode root = objectMapper.readTree(json).get("data");
        List<Province> provincesToSave = new ArrayList<>();

        for (Iterator<Map.Entry<String, Province>> it = nameProvinceMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Province> entry = it.next();
            if (entry.getValue().getRegion().getId().equals(region.getId())) {
                it.remove();
            }
        }

        for (JsonNode node : root) {
            String provinceName = node.get("province").asText().isEmpty() ? "N/A" : node.get("province").asText();

            Province province = provinceRepository.findByNameAndRegion(provinceName, region);

            if (province == null) {
                province = new Province();
                province.setName(provinceName);
                province.setRegion(region);
                provincesToSave.add(province);
            }

            nameProvinceMap.put(provinceName, province);
            nameProvinceMap.put(provinceName.toLowerCase(), province);

            if (node.has("name")) {
                String altName = node.get("name").asText();
                if (!altName.isEmpty() && !altName.equals(provinceName)) {
                    nameProvinceMap.put(altName, province);
                    nameProvinceMap.put(altName.toLowerCase(), province);
                }
            }
        }

        if (!provincesToSave.isEmpty()) {
            provinceRepository.saveAll(provincesToSave);
            logger.info("They were saved {} new provinces", provincesToSave.size());
        }

        logger.info("Total number of provinces on the temporary map: {}", nameProvinceMap.size());
    }

    public void saveReports(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json).get("data");
        List<Report> reportsToSave = new ArrayList<>();

        for (JsonNode node : root) {
            if (!node.has("region") || !node.get("region").has("province")) {
                logger.warn("Node without province information: {}", node.toString());
                continue;
            }

            String provinceName = node.get("region").get("province").asText();
            if (provinceName.isEmpty() || provinceName.equals("N/A")) {
                if (node.get("region").has("name")) {
                    provinceName = node.get("region").get("name").asText();
                } else {
                    provinceName = "N/A";
                }
            }

            Province province = nameProvinceMap.get(provinceName);

            if (province == null) {
                logger.warn("Province not found on the temporary map: {}", provinceName);
                List<Province> similarProvinces = provinceRepository.findByNameContainingIgnoreCase(provinceName);
                if (!similarProvinces.isEmpty()) {
                    province = similarProvinces.get(0);
                    nameProvinceMap.put(provinceName, province);
                    logger.info("Similar province found: {}", province.getName());
                } else {
                    logger.warn("No province similar to: {}", provinceName);
                    continue;
                }
            }

            try {
                LocalDate reportDate = LocalDate.parse(node.get("date").asText());
                Report existingReport = reportRepository.findByProvinceAndDate(province, reportDate);

                if (existingReport != null) {
                    existingReport.setConfirmed(node.get("confirmed").asInt());
                    existingReport.setDeaths(node.get("deaths").asInt());
                    existingReport.setRecovered(node.get("recovered").asInt());
                    reportsToSave.add(existingReport);
                } else {
                    Report report = new Report();
                    report.setDate(reportDate);
                    report.setConfirmed(node.get("confirmed").asInt());
                    report.setDeaths(node.get("deaths").asInt());
                    report.setRecovered(node.get("recovered").asInt());
                    report.setProvince(province);
                    reportsToSave.add(report);
                }
            } catch (Exception e) {
                logger.error("Error processing report: {} - For province: {}", e.getMessage(), provinceName);
            }
        }

        if (!reportsToSave.isEmpty()) {
            reportRepository.saveAll(reportsToSave);
            logger.info("They were saved {} reports", reportsToSave.size());
        } else {
            logger.info("No reports found to save");
        }
    }
}

