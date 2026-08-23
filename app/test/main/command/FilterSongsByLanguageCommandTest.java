package main.command;

import main.command.language.FilterSongsByLanguageCommand;
import main.command.language.LanguagePlaylistContext;
import main.command.language.ILanguageDetector;
import main.models.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterSongsByLanguageCommandTest {

    @Test
    void execute_keepsTracksMatchingTargetLanguage() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.songs = List.of(new Song("track1", "some english lyrics here", List.of()));

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx);

        assertEquals(1, ctx.languageFilteredSongs.size());
        assertEquals("track1", ctx.languageFilteredSongs.get(0).id);
    }

    @Test
    void execute_removesTracksNotMatchingTargetLanguage() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.songs = List.of(new Song("track1", "letras en español aquí", List.of()));

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("es")).execute(ctx);

        assertTrue(ctx.languageFilteredSongs.isEmpty());
    }

    @Test
    void execute_skipsTracksWithNullLyrics() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.songs = List.of(new Song("track1")); // no lyrics

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx);

        assertTrue(ctx.languageFilteredSongs.isEmpty());
    }

    @Test
    void execute_filtersCorrectlyAcrossMixedLanguages() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.songs = List.of(
                new Song("track1", "english lyrics", List.of()),
                new Song("track2", "spanish lyrics", List.of())
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
        ctx.songs = List.of(new Song("track1", "some english lyrics", List.of()));

        new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx);

        assertEquals(1, ctx.languageFilteredSongs.size());
        assertEquals("track1", ctx.languageFilteredSongs.get(0).id);
    }

    @Test
    void execute_alwaysReturnsTrue() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.targetLanguage = "en";
        ctx.songs = List.of();

        assertTrue(new FilterSongsByLanguageCommand(new FakeLanguageDetector("en")).execute(ctx));
    }
}
