package main;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestBuilderExtensionsTest {

    private HttpRequest.Builder baseBuilder() {
        return HttpRequest.newBuilder().uri(URI.create("https://accounts.spotify.com/api/token"));
    }

    @Test
    void withAuthorizationHeaderEncodesCorrectly() {
        HttpRequest request = new HttpRequestBuilderExtensions(baseBuilder())
                .withAuthorizationHeader("clientid", "clientsecret")
                .withPost(HttpRequest.BodyPublishers.noBody())
                .build();

        String expected = "Basic " + Base64.getEncoder().encodeToString("clientid:clientsecret".getBytes());
        assertEquals(expected, request.headers().firstValue("Authorization").orElse(""));
    }

    @Test
    void withContentTypeSetsHeader() {
        HttpRequest request = new HttpRequestBuilderExtensions(baseBuilder())
                .withContentType("application/x-www-form-urlencoded")
                .withPost(HttpRequest.BodyPublishers.noBody())
                .build();

        assertEquals("application/x-www-form-urlencoded", request.headers().firstValue("Content-Type").orElse(""));
    }
}
