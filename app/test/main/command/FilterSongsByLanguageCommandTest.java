package main.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterSongsByLanguageCommandTest {

    @Test
    void execute_keepsTracksMatchingTargetLanguage() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.moodFilteredSongs = List.of(new Song("track1", 0.7, 0.8, "some english lyrics here"));

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx);

        assertEquals(1, ctx.languageFilteredSongs.size());
        assertEquals("track1", ctx.languageFilteredSongs.get(0).id);
    }

    @Test
    void execute_removesTracksNotMatchingTargetLanguage() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.moodFilteredSongs = List.of(new Song("track1", 0.5, 0.3, "letras en español aquí"));

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("es")).execute(ctx);

        assertTrue(ctx.languageFilteredSongs.isEmpty());
    }

    @Test
    void execute_skipsTracksWithNullLyrics() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.moodFilteredSongs = List.of(new Song("track1", 0.7, 0.8)); // no lyrics

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx);

        assertTrue(ctx.languageFilteredSongs.isEmpty());
    }

    @Test
    void execute_filtersCorrectlyAcrossMixedLanguages() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.moodFilteredSongs = List.of(
                new Song("track1", 0.7, 0.8, "english lyrics"),
                new Song("track2", 0.5, 0.3, "spanish lyrics")
        );

        ILanguageDetector mixed = text -> text.contains("english") ? "en" : "es";
        new FilterSongsByLanguageCommand(mixed).execute(ctx);

        assertEquals(1, ctx.languageFilteredSongs.size());
        assertEquals("track1", ctx.languageFilteredSongs.get(0).id);
    }

    @Test
    void execute_isCaseInsensitiveForLanguageCode() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "EN";
        ctx.moodFilteredSongs = List.of(new Song("track1", 0.7, 0.8, "some english lyrics"));

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx);

        assertEquals(1, ctx.languageFilteredSongs.size());
        assertEquals("track1", ctx.languageFilteredSongs.get(0).id);
    }

    @Test
    void execute_alwaysReturnsTrue() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.moodFilteredSongs = List.of();

        assertTrue(new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx));
    }
}
