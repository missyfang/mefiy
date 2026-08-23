package main.command.commandContext;

import main.ISpotifyApiClient;
import main.models.Song;

import java.util.List;

// role interface for contexts that support creating a playlist and adding songs to it
public interface IPlaylistBuildableContext extends ICommandContext {
    String getAccessToken();
    ISpotifyApiClient getApiClient();
    String getUserId();
    String getPlaylistName();
    String getPlaylistId();
    void setPlaylistId(String playlistId);
    List<Song> getPlaylistSongs();
}
