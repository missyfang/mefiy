package main.command;

import java.util.ArrayList;
import java.util.List;
// all the info needed to build playlist from liked songs filtered by language and mood
public class LanguagePlaylistContext implements ICommandContext {

    public String accessToken;
    public String userId;
    public String targetLanguage;
    public String targetMood;
    public List<String> likedTrackIds = new ArrayList<>();
    public List<String> filteredTrackIds = new ArrayList<>();
    public String playlistId;
}
