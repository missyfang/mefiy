package main.command.artistmood;

import main.command.Song;
import main.command.commandContext.ICommandContext;

import java.util.List;

// role interface for contexts that support filtering songs by artist IDs
public interface IArtistFilterableContext extends ICommandContext {
    List<String> getTargetArtistIds();
    List<Song> getMoodFilteredSongs();
    List<Song> getArtistMoodFilteredSongs();
    void setArtistMoodFilteredSongs(List<Song> songs);
}
