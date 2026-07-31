package main.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/// get all the liked songs for the user
public class GetLikedSongsCommand implements ICommand {

    private static final String BASE_URL = "https://api.spotify.com/v1/me/tracks?limit=50&offset=";
    static final Pattern TRACK_URI_PATTERN = Pattern.compile("\"uri\":\"spotify:track:([^\"]+)\"");
    static final Pattern NEXT_PATTERN = Pattern.compile("\"next\":\"([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        LanguagePlaylistContext ctx = (LanguagePlaylistContext) context;

        List<String> trackIds = new ArrayList<>();
        int offset = 0;
        boolean hasMore = true;

        try {
            while (hasMore) {
                hasMore = handleResponse(SpotifyApiClient.get(BASE_URL + offset, ctx.accessToken), trackIds);
                // this is gonna take forever to get all liked songs, we should cache this for all features.
                offset += 50;
            }
            ctx.likedTrackIds = trackIds;
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    boolean handleResponse(String body, List<String> trackIds) {
        trackIds.addAll(SpotifyResponseParser.parseAll(body, TRACK_URI_PATTERN));
        return SpotifyResponseParser.hasMatch(body, NEXT_PATTERN);
    }
}
