package main;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.net.http.*;

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

    public TokenResult TryGetToken(int port) throws Exception {
        // open login on desktop
        URI authUri = new CodeURIBuilder()
                .withBaseUrl("https://accounts.spotify.com/authorize")
                .withClientId(clientId)
                .withResponseType("code")
                .withRedirectUri(redirectUri)
                .withScope("user-read-private user-library-read playlist-modify-private playlist-modify-public")
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

        // pass code to get token
        HttpRequest request = new HttpRequestBuilderExtensions(
                HttpRequest.newBuilder().uri(URI.create("https://accounts.spotify.com/api/token")))
                .withAuthorizationHeader(clientId, clientSecret)
                .withContentType("application/x-www-form-urlencoded")
                .withPost(new TokenRequestBodyBuilder()
                        .withGrantType("authorization_code")
                        .withAuthCode(authCode)
                        .withRedirectUri(redirectUri)
                        .build())
                .build();

        try {
            String body = http.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return new TokenResult(body.contains("access_token"), body);
        } catch (Exception e) {
            return new TokenResult(false, e.getMessage());
        }
    }
}
