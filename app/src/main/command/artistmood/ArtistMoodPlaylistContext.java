package main.command.artistmood;

import main.Mood;
import main.command.commandContext.IMoodFilterableContext;
import main.command.commandContext.ISpotifyContext;
import main.command.Song;

import java.util.ArrayList;
import java.util.List;

// context for building a playlist from liked songs filtered by mood and a list of target artist IDs
public class ArtistMoodPlaylistContext implements ISpotifyContext, IMoodFilterableContext, IArtistFilterableContext {

    public String accessToken;
    public String userId;
    public Mood targetMood;
    public List<String> targetArtistIds = new ArrayList<>();
    public List<String> likedTrackIds = new ArrayList<>();
    public List<Song> songs = new ArrayList<>();
    public List<Song> moodFilteredSongs = new ArrayList<>();
    public List<Song> artistMoodFilteredSongs = new ArrayList<>();
    public String playlistId;

    @Override public String getAccessToken() { return accessToken; }
    @Override public String getUserId() { return userId; }
    @Override public void setUserId(String userId) { this.userId = userId; }
    @Override public List<String> getLikedTrackIds() { return likedTrackIds; }
    @Override public void setLikedTrackIds(List<String> trackIds) { this.likedTrackIds = trackIds; }
    @Override public List<Song> getSongs() { return songs; }
    @Override public void setSongs(List<Song> songs) { this.songs = songs; }
    @Override public Mood getTargetMood() { return targetMood; }
    @Override public List<Song> getMoodFilteredSongs() { return moodFilteredSongs; }
    @Override public void setMoodFilteredSongs(List<Song> songs) { this.moodFilteredSongs = songs; }
    @Override public List<String> getTargetArtistIds() { return targetArtistIds; }
    @Override public List<Song> getArtistMoodFilteredSongs() { return artistMoodFilteredSongs; }
    @Override public void setArtistMoodFilteredSongs(List<Song> songs) { this.artistMoodFilteredSongs = songs; }
}
