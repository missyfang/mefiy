package main.command.sharedCommands;

import main.command.ICommand;
import main.command.Song;
import main.command.commandContext.ISpotifyContext;
import main.command.SpotifyApiClient;
import main.command.SpotifyResponseParser;
import main.command.commandContext.ICommandContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// get all the liked songs for the user
public class GetLikedSongsCommand implements ICommand {

    private static final String BASE_URL = "https://api.spotify.com/v1/me/tracks?limit=50&offset=";
    public static final Pattern TRACK_URI_PATTERN = Pattern.compile("\"uri\":\"spotify:track:([^\"]+)\"");
    public static final Pattern ARTIST_URI_PATTERN = Pattern.compile("\"uri\":\"spotify:artist:([^\"]+)\"");
    public static final Pattern NEXT_PATTERN = Pattern.compile("\"next\":\"([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        ISpotifyContext ctx = (ISpotifyContext) context;

        List<String> trackIds = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        int offset = 0;
        boolean hasMore = true;

        try {
            while (hasMore) {
                String body = SpotifyApiClient.get(BASE_URL + offset, ctx.getAccessToken());
                hasMore = handleResponse(body, trackIds, songs);
                // this is gonna take forever to get all liked songs, we should cache this for all features.
                offset += 50;
            }
            ctx.setLikedTrackIds(trackIds);
            ctx.setSongs(songs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean handleResponse(String body, List<String> trackIds, List<Song> songs) {
        // parse artist URIs and their positions
        Matcher artistMatcher = ARTIST_URI_PATTERN.matcher(body);
        List<Integer> artistStarts = new ArrayList<>();
        List<String> artistIds = new ArrayList<>();
        while (artistMatcher.find()) {
            artistStarts.add(artistMatcher.start());
            artistIds.add(artistMatcher.group(1));
        }

        // parse track URIs and correlate each track with its preceding artist URIs
        Matcher trackMatcher = TRACK_URI_PATTERN.matcher(body);
        int artistIdx = 0;
        while (trackMatcher.find()) {
            String trackId = trackMatcher.group(1);
            int trackUriPos = trackMatcher.start();

            List<String> trackArtists = new ArrayList<>();
            while (artistIdx < artistStarts.size() && artistStarts.get(artistIdx) < trackUriPos) {
                trackArtists.add(artistIds.get(artistIdx));
                artistIdx++;
            }

            trackIds.add(trackId);
            songs.add(new Song(trackId, null, trackArtists));
        }

        return SpotifyResponseParser.hasMatch(body, NEXT_PATTERN);
    }
}
