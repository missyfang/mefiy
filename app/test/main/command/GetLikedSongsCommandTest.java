package main.command;

import main.command.language.LanguagePlaylistContext;
import main.command.sharedCommands.GetLikedSongsCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetLikedSongsCommandTest {

    @Test
    void handleResponse_addsTrackIdsToAccumulator() {
        String body = "{\"items\":[" +
                "{\"track\":{\"uri\":\"spotify:track:abc123\"}}," +
                "{\"track\":{\"uri\":\"spotify:track:def456\"}}" +
                "],\"next\":null}";
        List<String> trackIds = new ArrayList<>();

        new GetLikedSongsCommand().handleResponse(body, trackIds);

        assertEquals(List.of("abc123", "def456"), trackIds);
    }

    @Test
    void handleResponse_returnsTrueWhenNextPageExists() {
        String body = "{\"items\":[{\"track\":{\"uri\":\"spotify:track:abc123\"}}]," +
                "\"next\":\"https://api.spotify.com/v1/me/tracks?offset=50\"}";

        boolean hasMore = new GetLikedSongsCommand().handleResponse(body, new ArrayList<>());

        assertTrue(hasMore);
    }

    @Test
    void handleResponse_returnsFalseOnLastPage() {
        String body = "{\"items\":[{\"track\":{\"uri\":\"spotify:track:abc123\"}}],\"next\":null}";

        boolean hasMore = new GetLikedSongsCommand().handleResponse(body, new ArrayList<>());

        assertFalse(hasMore);
    }

    @Test
    void execute_returnsFalseWhenTokenIsInvalid() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.accessToken = "invalid_token";

        assertFalse(new GetLikedSongsCommand().execute(ctx));
        assertTrue(ctx.likedTrackIds.isEmpty());
    }
}
