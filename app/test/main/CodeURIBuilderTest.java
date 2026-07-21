package main;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeURIBuilderTest {

    @Test
    void buildProducesCorrectUri() {
        URI uri = new CodeURIBuilder()
                .withBaseUrl("https://accounts.spotify.com/authorize")
                .withClientId("clientid")
                .withResponseType("code")
                .withRedirectUri("http://localhost:3000")
                .withScope("user-read-private")
                .build();

        assertEquals(
                "https://accounts.spotify.com/authorize" +
                "?client_id=clientid" +
                "&response_type=code" +
                "&redirect_uri=http%3A%2F%2Flocalhost%3A3000" +
                "&scope=user-read-private",
                uri.toString()
        );
    }
}
