package main.command;

import main.HttpRequestBuilderExtensions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// wrapper for all the calls to spotify api
public class SpotifyApiClient {

    private static final HttpClient http = HttpClient.newHttpClient();

    public static String get(String url, String token) throws Exception {
        HttpRequest request = new HttpRequestBuilderExtensions(
                HttpRequest.newBuilder().uri(URI.create(url)))
                .withBearerToken(token)
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new Exception("HTTP " + response.statusCode());
        return response.body();
    }
}
