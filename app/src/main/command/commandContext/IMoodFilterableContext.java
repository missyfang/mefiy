package main.command.commandContext;

import main.Mood;
import main.command.Song;

import java.util.List;

// role interface for contexts that support mood-based song filtering
public interface IMoodFilterableContext extends ICommandContext {
    Mood getTargetMood();
    List<Song> getSongs();
    List<Song> getMoodFilteredSongs();
    void setMoodFilteredSongs(List<Song> songs);
}
