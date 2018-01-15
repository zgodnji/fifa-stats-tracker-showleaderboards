package com.zgodnji.fifastatstracker;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RequestScoped
@Path("showleaderboards")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_HTML)
public class ShowLeaderboardResource {

    @Inject
    @DiscoverService(value = "games-service", environment = "dev", version = "1.0.0")
    private String gamesUrlString;

    @Inject
    @DiscoverService(value = "leaderboards-service", environment = "dev", version = "1.0.0")
    private String leaderboardsUrlString;

    @Inject
    @DiscoverService(value = "users-service", environment = "dev", version = "1.0.0")
    private String usersUrlString;

    @GET
    @Path("{gameId}")
    public Response showLeaderboard(@PathParam("gameId") String gameId) {

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Leaderboard</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"max-width: 900px; border: 1px black dashed; text-align: center\">\n" +
                "        <div>\n" +
                "            <h1>Leaderboard for ";

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object objG = JSONValue.parse(game.toString());
        JSONObject gameObj = (JSONObject) objG;

        html += gameObj.get("title");
        html += "</h1>\n" +
                "            <ol>\n";

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(leaderboardsUrlString + "/v1/leaderboards/" + gameId + "/leaderboard");
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
                response.append(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Object obj = JSONValue.parse(response.toString());
        JSONArray results = (JSONArray) obj;


        for (int i = 0; i < results.size(); i++) {
            StringBuilder tmpUser = new StringBuilder();
            JSONObject user = (JSONObject) results.get(i);

            try {
                URL url = new URL(usersUrlString + "/v1/users/" + user.get("player"));
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
                    tmpUser.append(output);
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Object objU = JSONValue.parse(tmpUser.toString());
            JSONObject tmpUserObj = (JSONObject) objU;

            html += "<li>" + tmpUserObj.get("firstName") + " " + tmpUserObj.get("lastName") + "</li>";
        }

        html += "               </ol>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        // Get
        return Response.ok(html).build();
    }
}
