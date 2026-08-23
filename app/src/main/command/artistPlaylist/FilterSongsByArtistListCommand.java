package main.command.artistPlaylist;

import main.command.ICommand;
import main.models.Song;
import main.command.commandContext.ICommandContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// filters songs to only those whose artist IDs overlap with the target artist ID list
public class FilterSongsByArtistListCommand implements ICommand {

    @Override
    public boolean execute(ICommandContext context) {
        IArtistFilterableContext ctx = (IArtistFilterableContext) context;

        Set<String> targetIds = new HashSet<>(ctx.getTargetArtistIds());

        List<Song> filtered = new ArrayList<>();
        for (Song song : ctx.getSongs()) {
            boolean matchesArtist = song.artistIds.stream().anyMatch(targetIds::contains);
            if (matchesArtist) {
                filtered.add(song);
            }
        }

        ctx.setArtistFilteredSongs(filtered);
        return true;
    }
}
