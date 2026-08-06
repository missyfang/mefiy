package main.command;

import java.util.ArrayList;
import java.util.List;

// filters songs by language of its lyrics
public class FilterSongsByLanguageCommand implements ICommand {

    private final ILanguageDetector detector;

    public FilterSongsByLanguageCommand(ILanguageDetector detector) {
        this.detector = detector;
    }

    @Override
    public boolean execute(ICommandContext context) {
        LanguagePlaylistContext ctx = (LanguagePlaylistContext) context;
        List<Song> filtered = new ArrayList<>();
        for (Song song : ctx.moodFilteredSongs) {
            if (song.lyrics == null) continue;
            try {
                String language = detector.detect(song.lyrics);
                if (ctx.targetLanguage.equalsIgnoreCase(language)) {
                    filtered.add(song);
                }
            } catch (Exception e) {
              throw new Exception("failed to detect language");
            }
        }
        ctx.languageFilteredSongs = filtered;
        return true;
    }
}
