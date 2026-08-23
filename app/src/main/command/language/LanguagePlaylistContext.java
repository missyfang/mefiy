package main.command.language;

import main.command.commandContext.ISpotifyContext;
import main.models.Song;

import java.util.ArrayList;
import java.util.List;

// all the info needed to build a playlist from liked songs filtered by language
public class LanguagePlaylistContext implements ISpotifyContext, ILanguageFilterableContext {

    public String accessToken;
    public String userId;
    public String targetLanguage;
    public List<String> likedTrackIds = new ArrayList<>();
    public List<Song> songs = new ArrayList<>();
    public List<Song> languageFilteredSongs = new ArrayList<>();
    public String playlistId;

    @Override public String getAccessToken() { return accessToken; }
    @Override public String getUserId() { return userId; }
    @Override public void setUserId(String userId) { this.userId = userId; }
    @Override public List<String> getLikedTrackIds() { return likedTrackIds; }
    @Override public void setLikedTrackIds(List<String> trackIds) { this.likedTrackIds = trackIds; }
    @Override public List<Song> getSongs() { return songs; }
    @Override public void setSongs(List<Song> songs) { this.songs = songs; }
    @Override public String getTargetLanguage() { return targetLanguage; }
    @Override public List<Song> getLanguageFilteredSongs() { return languageFilteredSongs; }
    @Override public void setLanguageFilteredSongs(List<Song> songs) { this.languageFilteredSongs = songs; }
}
