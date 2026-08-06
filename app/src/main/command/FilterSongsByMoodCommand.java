package main.command;

import java.util.ArrayList;
import java.util.List;

// filters ctx.songs by mood and writes matching track ids to ctx.filteredTrackIds
public class FilterSongsByMoodCommand implements ICommand {

    @Override
    public boolean execute(ICommandContext context) {
        LanguagePlaylistContext ctx = (LanguagePlaylistContext) context;
        List<String> filtered = new ArrayList<>();
        for (Song song : ctx.songs) {
            if (matchesMood(ctx.targetMood, song.valence, song.energy)) {
                filtered.add(song.id);
            }
        }
        ctx.filteredTrackIds = filtered;
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
