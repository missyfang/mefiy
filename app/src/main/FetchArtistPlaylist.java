package main;

import com.sun.net.httpserver.HttpExchange;
import main.command.artistPlaylist.ArtistPlaylistContext;
import main.command.artistPlaylist.ArtistPlaylistWorkflow;
import main.models.Song;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchArtistPlaylist {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("\"token\":\"([^\"]+)\"");
    private static final Pattern ARTIST_IDS_PATTERN = Pattern.compile("\"artistIds\":\"([^\"]+)\"");
    private static final Pattern PLAYLIST_NAME_PATTERN = Pattern.compile("\"playlistName\":\"([^\"]+)\"");

    public void handle(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        String token = parseField(body, TOKEN_PATTERN);
        String artistIdsRaw = parseField(body, ARTIST_IDS_PATTERN);

        if (token == null || artistIdsRaw == null) {
            sendResponse(exchange, 400, "{\"success\":false,\"message\":\"Missing token or artistIds\"}");
            return;
        }

        List<String> artistIds = Arrays.stream(artistIdsRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        String playlistName = parseField(body, PLAYLIST_NAME_PATTERN);

        ArtistPlaylistContext ctx = new ArtistPlaylistContext();
        ctx.accessToken = token;
        ctx.targetArtistIds.addAll(artistIds);
        if (playlistName != null && !playlistName.isEmpty()) {
            ctx.playlistName = playlistName;
        }

        boolean success = new ArtistPlaylistWorkflow().run(ctx);
        if (success) {
            sendResponse(exchange, 200, toJson(ctx));
        } else {
            sendResponse(exchange, 500, "{\"success\":false,\"message\":\"Failed to filter songs\"}");
        }
    }

    private String toJson(ArtistPlaylistContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true");
        sb.append(",\"playlistId\":\"").append(escape(ctx.playlistId)).append("\"");
        sb.append(",\"songs\":[");
        for (int i = 0; i < ctx.artistFilteredSongs.size(); i++) {
            Song s = ctx.artistFilteredSongs.get(i);
            if (i > 0) sb.append(",");
            sb.append("{\"trackId\":\"").append(escape(s.id)).append("\"");
            sb.append(",\"artistNames\":[");
            for (int j = 0; j < s.artistNames.size(); j++) {
                if (j > 0) sb.append(",");
                sb.append("\"").append(escape(s.artistNames.get(j))).append("\"");
            }
            sb.append("]}");
        }
        sb.append("]}");
        return sb.toString();
    }

    private String parseField(String body, Pattern pattern) {
        Matcher m = pattern.matcher(body);
        return m.find() ? m.group(1) : null;
    }

    private void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
