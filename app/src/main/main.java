package main;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class main {
    private String CLIENT_ID = "8a700cacda704fa28a412c549b93b1f1";
    private String CLIENT_SECRET = "";
    private String REDIRECT_URI = "http://127.0.0.1:3000";

    private static final HttpClient http = HttpClient.newHttpClient();

    void main(String[] args) throws Exception {
        System.out.println("enter client secret: ");

        Scanner scanner = new Scanner(System.in);
        CLIENT_SECRET = scanner.next();
        String token = GetToken(3000);
        System.out.println("got the token: " + token);
    }

    private String GetToken(int port) throws Exception {
        // open login on desktop
        Desktop.getDesktop().browse(URI.create("https://accounts.spotify.com/authorize"
                + "?client_id=" + CLIENT_ID
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)
                + "&scope=user-read-private"));

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
                .header("Authorization", "Basic " +  Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString( "grant_type=authorization_code"
                        + "&code=" + authCode
                        + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)))
                .build();

        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        }
        catch (Exception e) {
                return e.getMessage();
        }

    }
}
