package main.command.language;

import main.command.Song;
import main.command.commandContext.ICommandContext;

import java.util.List;

// role interface for contexts that support language-based song filtering
public interface ILanguageFilterableContext extends ICommandContext {
    String getTargetLanguage();
    List<Song> getSongs();
    List<Song> getLanguageFilteredSongs();
    void setLanguageFilteredSongs(List<Song> songs);
}
