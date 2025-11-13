package org.zeus.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

@Service
public class PersonalityRandomGeneratorService {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Random random = new Random();

    public static String generateFirstName() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://randomuser.me/api/"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Парсим имя из JSON вручную, без библиотек:
            String body = response.body();
            int start = body.indexOf("\"first\":\"") + 9;
            int end = body.indexOf("\"", start);
            return body.substring(start, end);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "DefaultFirst";
        }
    }

    public static String generateLastName() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://randomuser.me/api/"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            int start = body.indexOf("\"last\":\"") + 8;
            int end = body.indexOf("\"", start);
            return body.substring(start, end);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "DefaultLast";
        }
    }
}
