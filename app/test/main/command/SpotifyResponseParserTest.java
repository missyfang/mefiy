package main.command;

import main.command.sharedCommands.GetLikedSongsCommand;
import main.command.sharedCommands.GetUserCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpotifyResponseParserTest {

    @Test
    void parseFirst_returnsUserIdFromResponse() {
        String body = "{\"id\":\"wizzler\",\"display_name\":\"Jane\"}";
        assertEquals("wizzler", SpotifyResponseParser.parseFirst(body, GetUserCommand.USER_ID_PATTERN));
    }

    @Test
    void parseFirst_returnsNullWhenFieldMissing() {
        String body = "{\"display_name\":\"Jane\"}";
        assertNull(SpotifyResponseParser.parseFirst(body, GetUserCommand.USER_ID_PATTERN));
    }

    @Test
    void parseAll_returnsAllTrackIds() {
        String body = "{\"items\":[" +
                "{\"track\":{\"uri\":\"spotify:track:abc123\"}}," +
                "{\"track\":{\"uri\":\"spotify:track:def456\"}}" +
                "]}";
        List<String> ids = SpotifyResponseParser.parseAll(body, GetLikedSongsCommand.TRACK_URI_PATTERN);
        assertEquals(List.of("abc123", "def456"), ids);
    }

    @Test
    void parseAll_returnsEmptyListWhenNoTracks() {
        String body = "{\"items\":[]}";
        List<String> ids = SpotifyResponseParser.parseAll(body, GetLikedSongsCommand.TRACK_URI_PATTERN);
        assertTrue(ids.isEmpty());
    }

    @Test
    void parseAll_doesNotMatchAlbumOrArtistIds() {
        String body = "{\"track\":{\"album\":{\"id\":\"albumid\"},\"artists\":[{\"id\":\"artistid\"}]," +
                "\"uri\":\"spotify:track:trackid\"}}";
        List<String> ids = SpotifyResponseParser.parseAll(body, GetLikedSongsCommand.TRACK_URI_PATTERN);
        assertEquals(List.of("trackid"), ids);
    }

    @Test
    void hasMatch_returnsTrueWhenNextPageExists() {
        String body = "{\"next\":\"https://api.spotify.com/v1/me/tracks?offset=50\",\"items\":[]}";
        assertTrue(SpotifyResponseParser.hasMatch(body, GetLikedSongsCommand.NEXT_PATTERN));
    }

    @Test
    void hasMatch_returnsFalseWhenNextIsNull() {
        String body = "{\"next\":null,\"items\":[]}";
        assertFalse(SpotifyResponseParser.hasMatch(body, GetLikedSongsCommand.NEXT_PATTERN));
    }

    @Test
    void hasMatch_returnsFalseWhenNextFieldMissing() {
        String body = "{\"items\":[]}";
        assertFalse(SpotifyResponseParser.hasMatch(body, GetLikedSongsCommand.NEXT_PATTERN));
    }
}
