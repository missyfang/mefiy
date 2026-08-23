package main.command.language;

import main.command.ICommand;
import main.command.Song;
import main.command.commandContext.ICommandContext;

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
        ILanguageFilterableContext ctx = (ILanguageFilterableContext) context;
        List<Song> filtered = new ArrayList<>();
        for (Song song : ctx.getMoodFilteredSongs()) {
            if (song.lyrics == null) continue;
            try {
                String language = detector.detect(song.lyrics);
                if (ctx.getTargetLanguage().equalsIgnoreCase(language)) {
                    filtered.add(song);
                }
            } catch (Exception e) {
                throw new RuntimeException("failed to detect language", e);
            }
        }
        ctx.setLanguageFilteredSongs(filtered);
        return true;
    }
}
