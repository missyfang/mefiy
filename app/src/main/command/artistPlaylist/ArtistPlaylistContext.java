package main.command.artistPlaylist;

import main.command.commandContext.IPlaylistBuildableContext;
import main.command.commandContext.ISpotifyContext;
import main.models.Song;

import java.util.ArrayList;
import java.util.List;

// context for building a playlist from liked songs filtered by a list of target artist IDs
public class ArtistPlaylistContext implements ISpotifyContext, IArtistFilterableContext, IPlaylistBuildableContext {

    public String accessToken;
    public String userId;
    public String playlistName = "Artist Playlist";
    public List<String> targetArtistIds = new ArrayList<>();
    public List<String> likedTrackIds = new ArrayList<>();
    public List<Song> songs = new ArrayList<>();
    public List<Song> artistFilteredSongs = new ArrayList<>();
    public String playlistId;

    @Override public String getAccessToken() { return accessToken; }
    @Override public String getUserId() { return userId; }
    @Override public void setUserId(String userId) { this.userId = userId; }
    @Override public List<String> getLikedTrackIds() { return likedTrackIds; }
    @Override public void setLikedTrackIds(List<String> trackIds) { this.likedTrackIds = trackIds; }
    @Override public List<Song> getSongs() { return songs; }
    @Override public void setSongs(List<Song> songs) { this.songs = songs; }
    @Override public List<String> getTargetArtistIds() { return targetArtistIds; }
    @Override public List<Song> getArtistFilteredSongs() { return artistFilteredSongs; }
    @Override public void setArtistFilteredSongs(List<Song> songs) { this.artistFilteredSongs = songs; }
    @Override public String getPlaylistName() { return playlistName; }
    @Override public String getPlaylistId() { return playlistId; }
    @Override public void setPlaylistId(String playlistId) { this.playlistId = playlistId; }
    @Override public List<Song> getPlaylistSongs() { return artistFilteredSongs; }
}
