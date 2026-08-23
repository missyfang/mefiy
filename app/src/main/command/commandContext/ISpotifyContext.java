package main.command.commandContext;

import main.command.Song;

import java.util.List;

// role interface for commands that call the Spotify API and work with track data
public interface ISpotifyContext extends ICommandContext {
    String getAccessToken();
    String getUserId();
    void setUserId(String userId);
    List<String> getLikedTrackIds();
    void setLikedTrackIds(List<String> trackIds);
    List<Song> getSongs();
    void setSongs(List<Song> songs);
}
