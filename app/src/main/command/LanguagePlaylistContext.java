package main.command;

import java.util.ArrayList;
import java.util.List;

// all the info needed to build a playlist from liked songs filtered by language and mood
public class LanguagePlaylistContext implements ICommandContext {

    public String accessToken;
    public String userId;
    public String targetLanguage;
    public Mood targetMood;
    public List<String> likedTrackIds = new ArrayList<>();
    public List<Song> songs = new ArrayList<>();
    public List<Song> moodFilteredSongs = new ArrayList<>();
    public List<Song> languageFilteredSongs = new ArrayList<>();
    public String playlistId;
}
