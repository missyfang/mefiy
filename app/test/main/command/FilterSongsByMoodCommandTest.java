package main.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterSongsByMoodCommandTest {

    @Test
    void execute_filtersHappySongs() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetMood = Mood.HAPPY;
        ctx.songs = List.of(
                new Song("track1", 0.7, 0.8),  // valence 0.8 - passes
                new Song("track2", 0.5, 0.3)   // valence 0.3 - fails
        );

        new FilterSongsByMoodCommand().execute(ctx);

        assertEquals(List.of("track1"), ctx.filteredTrackIds);
    }

    @Test
    void execute_filtersSadSongs() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetMood = Mood.SAD;
        ctx.songs = List.of(
                new Song("track1", 0.2, 0.2),  // valence 0.2 - passes
                new Song("track2", 0.5, 0.7)   // valence 0.7 - fails
        );

        new FilterSongsByMoodCommand().execute(ctx);

        assertEquals(List.of("track1"), ctx.filteredTrackIds);
    }

    @Test
    void execute_filtersEnergeticSongs() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetMood = Mood.ENERGETIC;
        ctx.songs = List.of(
                new Song("track1", 0.85, 0.5),  // energy 0.85 - passes
                new Song("track2", 0.5, 0.8)    // energy 0.5 - fails
        );

        new FilterSongsByMoodCommand().execute(ctx);

        assertEquals(List.of("track1"), ctx.filteredTrackIds);
    }

    @Test
    void execute_filtersChillSongs() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetMood = Mood.CHILL;
        ctx.songs = List.of(
                new Song("track1", 0.3, 0.7),  // energy 0.3 - passes
                new Song("track2", 0.8, 0.6)   // energy 0.8 - fails
        );

        new FilterSongsByMoodCommand().execute(ctx);

        assertEquals(List.of("track1"), ctx.filteredTrackIds);
    }

    @Test
    void matchesMood_happyRequiresHighValence() {
        assertTrue(FilterSongsByMoodCommand.matchesMood(Mood.HAPPY, 0.6, 0.5));
        assertFalse(FilterSongsByMoodCommand.matchesMood(Mood.HAPPY, 0.59, 0.9));
    }

    @Test
    void matchesMood_sadRequiresLowValence() {
        assertTrue(FilterSongsByMoodCommand.matchesMood(Mood.SAD, 0.39, 0.5));
        assertFalse(FilterSongsByMoodCommand.matchesMood(Mood.SAD, 0.4, 0.1));
    }

    @Test
    void matchesMood_energeticRequiresHighEnergy() {
        assertTrue(FilterSongsByMoodCommand.matchesMood(Mood.ENERGETIC, 0.2, 0.7));
        assertFalse(FilterSongsByMoodCommand.matchesMood(Mood.ENERGETIC, 0.9, 0.69));
    }

    @Test
    void matchesMood_chillRequiresLowEnergy() {
        assertTrue(FilterSongsByMoodCommand.matchesMood(Mood.CHILL, 0.8, 0.39));
        assertFalse(FilterSongsByMoodCommand.matchesMood(Mood.CHILL, 0.2, 0.4));
    }
}
