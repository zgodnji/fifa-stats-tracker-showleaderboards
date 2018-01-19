package com.zgodnji.fifastatstracker.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.fault.tolerance.annotations.CommandKey;
import com.kumuluz.ee.fault.tolerance.annotations.GroupKey;
import org.eclipse.microprofile.faulttolerance.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.temporal.ChronoUnit;

@RequestScoped
@Bulkhead
@GroupKey("games")
public class GamesBean {

    @Inject
    @DiscoverService(value = "games-service", environment = "dev", version = "1.0.0")
    private String gamesUrlString;

    @CircuitBreaker
    @Fallback(fallbackMethod = "getGameTitleFallback")
    @CommandKey("http-find-game")
    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    public String getGameTitle(String gameId) {

        StringBuilder game = new StringBuilder();

        try {
            URL url = new URL(gamesUrlString + "/v1/games/" + gameId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                game.append(output);
            }

            conn.disconnect();
        } catch(Exception e) {
            throw new InternalServerErrorException("Games not available.");
        }

        System.out.println("Going to sleep");
        try {
            Thread.sleep(4000);
        } catch (Exception e) {

        }
        System.out.println("Wake up!");


        Object objG = JSONValue.parse(game.toString());
        JSONObject gameObj = (JSONObject) objG;

        return (String) gameObj.get("title");
    }

    public String getGameTitleFallback(String gameId) {
        return "Game title of game with id: " + gameId + " is currently unavailable.";
    }
}

