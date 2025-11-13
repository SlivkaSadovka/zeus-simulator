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

    public static String generateFavoriteJoke() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            int start = body.indexOf("\"value\":\"") + 9;
            int end = body.indexOf("\"", start);
            return body.substring(start, end);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "No joke available";
        }
    }

    public static String generateFavoriteQuote() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.quotable.io/random"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            int start = body.indexOf("\"content\":\"") + 11;
            int end = body.indexOf("\"", start);
            return body.substring(start, end);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "No quote available";
        }
    }

    public static String generateFavoriteAnimal() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://zoo-animal-api.herokuapp.com/animals/rand"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            int start = body.indexOf("\"name\":\"") + 8;
            int end = body.indexOf("\"", start);
            return body.substring(start, end);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Unknown animal";
        }
    }
}
