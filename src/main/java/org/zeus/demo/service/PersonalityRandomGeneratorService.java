package org.zeus.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PersonalityRandomGeneratorService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public String generateFirstName() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://randomuser.me/api/"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            return root.get("results").get(0).get("name").get("first").asText();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "DefaultFirst";
        }
    }

    public String generateLastName() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://randomuser.me/api/"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            return root.get("results").get(0).get("name").get("last").asText();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "DefaultLast";
        }
    }

    public String generateFavoriteJoke() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            return root.get("value").asText();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "No joke available";
        }
    }

    public String generateFavoriteQuote() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://aot-api.vercel.app/quote"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            return root.get("quote").asText();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "No quote available";
        }
    }

    public String generateFavoriteBook() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://potterapi-fedeperin.vercel.app/en/books/random"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            return root.get("title").asText();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Unknown book";
        }
    }
}
