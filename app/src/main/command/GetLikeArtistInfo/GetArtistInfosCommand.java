package main.command.GetLikeArtistInfo;

import main.command.*;
import main.command.commandContext.ICommandContext;
import main.command.commandContext.ISpotifyContext;
import main.models.Artist;
import main.models.Song;

import java.util.*;

// map links artist name to artist id
public class GetArtistInfosCommand implements ICommand {

    @Override
    public boolean execute(ICommandContext context) {
        LikedArtistInfoContext ctx = (LikedArtistInfoContext) context;
        ISpotifyContext spotifyCtx = (ISpotifyContext) context;

        Map<String, String> nameToId = new LinkedHashMap<>();
        for (Song song : spotifyCtx.getSongs()) {
            for (int i = 0; i < song.artistIds.size(); i++) {
                String id = song.artistIds.get(i);
                String name = i < song.artistNames.size() ? song.artistNames.get(i) : "Unknown";
                nameToId.putIfAbsent(name, id);
            }
        }

        List<Artist> artists = new ArrayList<>();
        for (Map.Entry<String, String> entry : nameToId.entrySet()) {
            artists.add(new Artist(entry.getValue(), entry.getKey()));
        }

        ctx.setArtists(artists);
        return true;
    }
}
