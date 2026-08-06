package main.command;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetSongAttributesCommandTest {

    private static String mockresponsebody(double energy, double valence) {
        return "{\"audio_features\":[{\"danceability\":0.5,\"energy\":" + energy +
                ",\"key\":5,\"loudness\":-5.0,\"mode\":1,\"speechiness\":0.05," +
                "\"acousticness\":0.1,\"instrumentalness\":0.0,\"liveness\":0.1," +
                "\"valence\":" + valence + ",\"tempo\":120.0}]}";
    }

    @Test
    void handleResponse_createsSongWithCorrectAttributes() {
        List<Song> songs = new ArrayList<>();
        new GetSongAttributesCommand().handleResponse(
                mockresponsebody(0.7, 0.8), List.of("track1"), songs);

        assertEquals(1, songs.size());
        assertEquals("track1", songs.get(0).id);
        assertEquals(0.7, songs.get(0).energy);
        assertEquals(0.8, songs.get(0).valence);
    }

    @Test
    void handleResponse_createsOneSongPerTrackInBatch() {
        String body = "{\"audio_features\":[" +
                "{\"danceability\":0.5,\"energy\":0.7,\"key\":5,\"loudness\":-5.0,\"mode\":1," +
                "\"speechiness\":0.05,\"acousticness\":0.1,\"instrumentalness\":0.0,\"liveness\":0.1," +
                "\"valence\":0.8,\"tempo\":120.0}," +
                "{\"danceability\":0.3,\"energy\":0.2,\"key\":2,\"loudness\":-10.0,\"mode\":0," +
                "\"speechiness\":0.04,\"acousticness\":0.8,\"instrumentalness\":0.0,\"liveness\":0.1," +
                "\"valence\":0.2,\"tempo\":80.0}]}";

        List<Song> songs = new ArrayList<>();
        new GetSongAttributesCommand().handleResponse(body, List.of("track1", "track2"), songs);

        assertEquals(2, songs.size());
        assertEquals("track1", songs.get(0).id);
        assertEquals("track2", songs.get(1).id);
        assertEquals(0.7, songs.get(0).energy);
        assertEquals(0.2, songs.get(1).energy);
    }

    @Test
    void execute_returnsFalseWhenTokenIsInvalid() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.accessToken = "invalid_token";
        ctx.likedTrackIds = List.of("track1");

        assertFalse(new GetSongAttributesCommand().execute(ctx));
        assertTrue(ctx.songs.isEmpty());
    }

    @Test
    void execute_returnsTrueAndSetsEmptySongsWhenNoLikedSongs() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.accessToken = "any_token";
        ctx.likedTrackIds = List.of();

        assertTrue(new GetSongAttributesCommand().execute(ctx));
        assertTrue(ctx.songs.isEmpty());
    }
}
