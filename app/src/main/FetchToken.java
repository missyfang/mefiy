package main;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FetchToken {
    private final AppConfig config;

    public FetchToken(AppConfig config) {
        this.config = config;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String clientSecret = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        int status;
        String response;
        try {
            TokenResult result = new AuthTokenGenerator(
                    config.getClientId(), clientSecret, config.getRedirectUri())
                    .TryGetToken(3000);
            if (result.success()) {
                status = 200;
                String escapedToken = result.token()
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r");
                response = "{\"success\":true,\"message\":\"Token retrieved!\",\"token\":\"" + escapedToken + "\"}";
            } else {
                status = 500;
                response = "{\"success\":false,\"message\":\"Token not retrieved!\"}";
            }
        } catch (Exception e) {
            status = 500;
            response = "{\"success\":false,\"message\":\"" + e.getMessage() + "\"}";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

}
