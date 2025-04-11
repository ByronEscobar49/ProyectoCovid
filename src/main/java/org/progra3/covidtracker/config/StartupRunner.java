package org.progra3.covidtracker.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class StartupRunner {
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(new DataFetcherThread());
                thread.start();
            }
        }, 15000); // 15 segundos
    }
}
