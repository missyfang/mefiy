package main.command.GetLikeArtistInfo;

import main.command.Artist;
import main.command.Song;
import main.command.commandContext.ISpotifyContext;

import java.util.ArrayList;
import java.util.List;

public class LikedArtistInfoContext implements ISpotifyContext {

    public String accessToken;
    public String userId;
    public List<String> likedTrackIds = new ArrayList<>();
    public List<Song> songs = new ArrayList<>();
    public List<Artist> artists = new ArrayList<>();

    @Override public String getAccessToken() { return accessToken; }
    @Override public String getUserId() { return userId; }
    @Override public void setUserId(String userId) { this.userId = userId; }
    @Override public List<String> getLikedTrackIds() { return likedTrackIds; }
    @Override public void setLikedTrackIds(List<String> trackIds) { this.likedTrackIds = trackIds; }
    @Override public List<Song> getSongs() { return songs; }
    @Override public void setSongs(List<Song> songs) { this.songs = songs; }

    public List<Artist> getArtists() { return artists; }
    public void setArtists(List<Artist> artists) { this.artists = artists; }
}
