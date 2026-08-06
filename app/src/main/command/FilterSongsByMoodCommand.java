package main.command;

import java.util.ArrayList;
import java.util.List;

// filters by mood
public class FilterSongsByMoodCommand implements ICommand {

    @Override
    public boolean execute(ICommandContext context) {
        LanguagePlaylistContext ctx = (LanguagePlaylistContext) context;
        List<Song> filtered = new ArrayList<>();
        for (Song song : ctx.songs) {
            if (matchesMood(ctx.targetMood, song.valence, song.energy)) {
                filtered.add(song);
            }
        }
        ctx.moodFilteredSongs = filtered;
        return true;
    }

    static boolean matchesMood(Mood mood, double valence, double energy) {
        return switch (mood) {
            case HAPPY     -> valence >= 0.6;
            case SAD       -> valence < 0.4;
            case ENERGETIC -> energy >= 0.7;
            case CHILL     -> energy < 0.4;
        };
    }
}
