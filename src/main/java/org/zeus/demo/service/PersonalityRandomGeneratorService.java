package org.zeus.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PersonalityRandomGeneratorService {

    private static final String API_URL = "https://randomuser.me/api/";

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String generateFirstName() {
        try {
            JSONObject name = fetchRandomName();
            return name.getString("first");
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    public static String generateLastName() {
        try {
            JSONObject name = fetchRandomName();
            return name.getString("last");
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private static JSONObject fetchRandomName() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(response.body());
        JSONArray results = json.getJSONArray("results");
        return results.getJSONObject(0).getJSONObject("name");
    }
}
