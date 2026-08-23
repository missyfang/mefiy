package main.command;

import main.command.language.LanguagePlaylistContext;
import main.command.sharedCommands.GetLikedSongsCommand;
import main.models.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetLikedSongsCommandTest {

    @Test
    void handleResponse_addsTrackIdsAndSongsWithArtistNames() {
        String body = "{\"items\":[" +
                "{\"track\":{\"artists\":[{\"name\":\"Artist One\",\"uri\":\"spotify:artist:art1\"}],\"uri\":\"spotify:track:abc123\"}}," +
                "{\"track\":{\"artists\":[{\"name\":\"Artist Two\",\"uri\":\"spotify:artist:art2\"}],\"uri\":\"spotify:track:def456\"}}" +
                "],\"next\":null}";
        List<String> trackIds = new ArrayList<>();
        List<Song> songs = new ArrayList<>();

        new GetLikedSongsCommand().handleResponse(body, trackIds, songs);

        assertEquals(List.of("abc123", "def456"), trackIds);
        assertEquals(2, songs.size());
        assertEquals("abc123", songs.get(0).id);
        assertEquals(List.of("art1"), songs.get(0).artistIds);
        assertEquals(List.of("Artist One"), songs.get(0).artistNames);
        assertEquals("def456", songs.get(1).id);
        assertEquals(List.of("art2"), songs.get(1).artistIds);
        assertEquals(List.of("Artist Two"), songs.get(1).artistNames);
    }

    @Test
    void handleResponse_returnsTrueWhenNextPageExists() {
        String body = "{\"items\":[{\"track\":{\"artists\":[{\"name\":\"A\",\"uri\":\"spotify:artist:art1\"}],\"uri\":\"spotify:track:abc123\"}}]," +
                "\"next\":\"https://api.spotify.com/v1/me/tracks?offset=50\"}";

        boolean hasMore = new GetLikedSongsCommand().handleResponse(body, new ArrayList<>(), new ArrayList<>());

        assertTrue(hasMore);
    }

    @Test
    void handleResponse_returnsFalseOnLastPage() {
        String body = "{\"items\":[{\"track\":{\"artists\":[{\"name\":\"A\",\"uri\":\"spotify:artist:art1\"}],\"uri\":\"spotify:track:abc123\"}}],\"next\":null}";

        boolean hasMore = new GetLikedSongsCommand().handleResponse(body, new ArrayList<>(), new ArrayList<>());

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
