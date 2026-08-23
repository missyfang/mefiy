package main.command.GetLikeArtistInfo;

import main.ISpotifyApiClient;
import main.models.Artist;
import main.models.Song;
import main.command.commandContext.ISpotifyContext;

import java.util.ArrayList;
import java.util.List;

public class LikedArtistInfoContext implements ISpotifyContext {

    public String accessToken;
    public String userId;
    private final ISpotifyApiClient apiClient;
    public List<String> likedTrackIds = new ArrayList<>();

    public LikedArtistInfoContext(ISpotifyApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public List<Song> songs = new ArrayList<>();
    public List<Artist> artists = new ArrayList<>();

    @Override public String getAccessToken() { return accessToken; }
    @Override public ISpotifyApiClient getApiClient() { return apiClient; }
    @Override public String getUserId() { return userId; }
    @Override public void setUserId(String userId) { this.userId = userId; }
    @Override public List<String> getLikedTrackIds() { return likedTrackIds; }
    @Override public void setLikedTrackIds(List<String> trackIds) { this.likedTrackIds = trackIds; }
    @Override public List<Song> getSongs() { return songs; }
    @Override public void setSongs(List<Song> songs) { this.songs = songs; }

    public List<Artist> getArtists() { return artists; }
    public void setArtists(List<Artist> artists) { this.artists = artists; }
}
