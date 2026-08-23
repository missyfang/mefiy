package main.command.sharedCommands;

import main.Mood;
import main.command.ICommand;
import main.command.Song;
import main.command.commandContext.IMoodFilterableContext;
import main.command.commandContext.ICommandContext;

import java.util.ArrayList;
import java.util.List;

// filters by mood
public class FilterSongsByMoodCommand implements ICommand {

    @Override
    public boolean execute(ICommandContext context) {
        IMoodFilterableContext ctx = (IMoodFilterableContext) context;
        List<Song> filtered = new ArrayList<>();
        for (Song song : ctx.getSongs()) {
            if (matchesMood(ctx.getTargetMood(), song.valence, song.energy)) {
                filtered.add(song);
            }
        }
        ctx.setMoodFilteredSongs(filtered);
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
