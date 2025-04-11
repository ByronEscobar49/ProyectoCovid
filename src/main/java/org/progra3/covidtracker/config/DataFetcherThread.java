package org.progra3.covidtracker.config;

import org.progra3.covidtracker.model.Province;
import org.progra3.covidtracker.model.Region;
import org.progra3.covidtracker.model.Report;
import org.progra3.covidtracker.service.CovidApiService;
import org.progra3.covidtracker.service.ProvinceService;
import org.progra3.covidtracker.service.RegionService;
import org.progra3.covidtracker.service.ReportService;

import java.util.List;

public class DataFetcherThread implements Runnable {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DataFetcherThread.class);
    private RegionService regionService;
    private ProvinceService provinceService;
    private ReportService reportService;
    private CovidApiService covidApiService;

    public DataFetcherThread() {

    }

    @Override
    public void run() {
        log.info("Starting data fetch thread...");

        List<Region> regions = CovidApiService.fetchRegions().getData();
        regionService.saveAll(regions);

        List<Province> provinces = covidApiService.fetchProvinces("GTM").getData();

        provinceService.saveAll(provinces);

        List<Report> reports = covidApiService.fetchReports("GTM", "2022-04-16").getData();
        reportService.saveAll(reports);

        log.info("Data fetch complete.");
    }
    public DataFetcherThread(RegionService regionService, ProvinceService provinceService, ReportService reportService, CovidApiService covidApiService) {
            this.regionService = regionService;
            this.provinceService = provinceService;

    }
}

