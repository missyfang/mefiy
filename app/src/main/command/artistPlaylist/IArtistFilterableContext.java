package main.command.artistPlaylist;

import main.models.Song;
import main.command.commandContext.ICommandContext;

import java.util.List;

// role interface for contexts that support filtering songs by artist IDs
public interface IArtistFilterableContext extends ICommandContext {
    List<String> getTargetArtistIds();
    List<Song> getSongs();
    List<Song> getArtistFilteredSongs();
    void setArtistFilteredSongs(List<Song> songs);
}
