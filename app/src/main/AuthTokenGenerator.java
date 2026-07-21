package main;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthTokenGenerator {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private static final HttpClient http = HttpClient.newHttpClient();

    public AuthTokenGenerator(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String GetToken(int port) throws Exception {
        // open login on desktop
        URI authUri = new CodeURIBuilder()
                .withBaseUrl("https://accounts.spotify.com/authorize")
                .withClientId(clientId)
                .withResponseType("code")
                .withRedirectUri(redirectUri)
                .withScope("user-read-private")
                .build();
        Desktop.getDesktop().browse(authUri);

        // grab code from redirect url spotify sends you to
        String authCode;
        try (ServerSocket server = new ServerSocket(port)) {
            try (Socket socket = server.accept()) {
                String redirectUrl = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                String query = redirectUrl.split(" ")[1];
                authCode = query.substring(query.indexOf("code=") + 5).split("&")[0];
            }
        }

        // TODO can we use a lib or something to make this easier??
        // pass code to get token
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code"
                        + "&code=" + authCode
                        + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)))
                .build();

        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
