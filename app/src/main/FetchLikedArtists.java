package main;

import com.sun.net.httpserver.HttpExchange;
import main.models.Artist;
import main.command.GetLikeArtistInfo.LikedArtistInfoContext;
import main.command.GetLikeArtistInfo.LikedArtistInfoWorkflow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FetchLikedArtists {

    public void handle(HttpExchange exchange) throws IOException {
        String token = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        LikedArtistInfoContext ctx = new LikedArtistInfoContext();
        ctx.accessToken = token;

        int status;
        String response;

        boolean success = new LikedArtistInfoWorkflow().run(ctx);
        if (success) {
            status = 200;
            response = toJson(ctx);
        } else {
            status = 500;
            response = "{\"success\":false,\"message\":\"Failed to fetch artist info\"}";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    private String toJson(LikedArtistInfoContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true,\"artists\":[");
        for (int i = 0; i < ctx.getArtists().size(); i++) {
            Artist a = ctx.getArtists().get(i);
            if (i > 0) sb.append(",");
            sb.append("{\"id\":\"").append(escape(a.id)).append("\"");
            sb.append(",\"name\":\"").append(escape(a.name)).append("\"}");
        }
        sb.append("]}");
        return sb.toString();
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
