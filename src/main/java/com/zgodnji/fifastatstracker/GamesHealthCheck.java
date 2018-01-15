package com.zgodnji.fifastatstracker;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Health
@ApplicationScoped
public class GamesHealthCheck implements HealthCheck {

    @Inject
    @DiscoverService(value = "games-service", environment = "dev", version = "1.0.0")
    private String url;

    private static final Logger LOG = Logger.getLogger(GamesHealthCheck.class.getSimpleName());

    @Override
    public HealthCheckResponse call() {
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");

            if (connection.getResponseCode() == 200) {
                return HealthCheckResponse.named(GamesHealthCheck.class.getSimpleName()).up().build();
            }
        } catch (Exception exception) {
            LOG.severe(exception.getMessage());
        }
        return HealthCheckResponse.named(GamesHealthCheck.class.getSimpleName()).down().build();
    }
}