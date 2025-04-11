package org.progra3.covidtracker.scheduler;

import org.progra3.covidtracker.service.CovidApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataFetchScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DataFetchScheduler.class);
    private final CovidApiService covidApiService;

    public DataFetchScheduler(CovidApiService covidApiService) {
        this.covidApiService = covidApiService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            try {
                logger.info("🕒 Esperando 15 segundos antes de ejecutar la carga de datos...");
                Thread.sleep(15000);
                covidApiService.fetchAndStoreData();
            } catch (InterruptedException e) {
                logger.error("❌ Error en el hilo de espera", e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
