package org.zeus.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PersonalityRandomGeneratorServiceTest {

    @Test
    void testGenerateFirstName() throws Exception {
        try (MockedStatic<HttpClient> clientMock = mockStatic(HttpClient.class)) {
            HttpClient mockClient = mock(HttpClient.class);
            @SuppressWarnings("unchecked")
            HttpResponse<String> mockResponse = mock(HttpResponse.class);

            clientMock.when(HttpClient::newHttpClient).thenReturn(mockClient);
            when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                    .thenReturn(mockResponse);

            when(mockResponse.body()).thenReturn("{\"results\":[{\"name\":{\"first\":\"John\"}}]}");

            String firstName = new PersonalityRandomGeneratorService().generateFirstName();
            assertEquals("John", firstName);
        }
    }

    @Test
    void testGenerateLastName() throws Exception {
        try (MockedStatic<HttpClient> clientMock = mockStatic(HttpClient.class)) {
            HttpClient mockClient = mock(HttpClient.class);
            @SuppressWarnings("unchecked")
            HttpResponse<String> mockResponse = mock(HttpResponse.class);

            clientMock.when(HttpClient::newHttpClient).thenReturn(mockClient);

            when(mockClient.send(
                    any(HttpRequest.class),
                    any(HttpResponse.BodyHandler.class))
            ).thenReturn(mockResponse);

            when(mockResponse.body()).thenReturn("{\"results\":[{\"name\":{\"last\":\"Doe\"}}]}");

            String lastName = new PersonalityRandomGeneratorService().generateLastName();
            assertEquals("Doe", lastName);
        }
    }

    @Test
    void testGenerateFavoriteJoke() throws Exception {
        try (MockedStatic<HttpClient> clientMock = mockStatic(HttpClient.class)) {
            HttpClient mockClient = mock(HttpClient.class);
            @SuppressWarnings("unchecked")
            HttpResponse<String> mockResponse = mock(HttpResponse.class);

            clientMock.when(HttpClient::newHttpClient).thenReturn(mockClient);

            when(mockClient.send(
                    any(HttpRequest.class),
                    any(HttpResponse.BodyHandler.class))
            ).thenReturn(mockResponse);

            when(mockResponse.body()).thenReturn("{\"value\":\"Funny joke!\"}");

            String joke = new PersonalityRandomGeneratorService().generateFavoriteJoke();
            assertEquals("Funny joke!", joke);
        }
    }

}
