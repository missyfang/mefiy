package main.command.sharedCommands;

import main.command.ICommand;
import main.models.Song;
import main.command.commandContext.ISpotifyContext;
import main.SpotifyResponseParser;
import main.command.commandContext.ICommandContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// get all the liked songs for the user
public class GetLikedSongsCommand implements ICommand {

    private static final String BASE_URL = "https://api.spotify.com/v1/me/tracks?limit=50&offset=";
    public static final Pattern TRACK_URI_PATTERN = Pattern.compile("\"uri\":\"spotify:track:([^\"]+)\"");
    // matches an artist object: captures name and ID from the same {...} block
    public static final Pattern ARTIST_PATTERN = Pattern.compile(
            "\"name\":\"([^\"]+)\"[^}]*\"uri\":\"spotify:artist:([^\"]+)\"");
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
                String body = ctx.getApiClient().get(BASE_URL + offset, ctx.getAccessToken());
                hasMore = handleResponse(body, trackIds, songs);
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
        // parse all artist objects: each match gives us (name, id) and a position
        Matcher artistMatcher = ARTIST_PATTERN.matcher(body);
        List<Integer> artistStarts = new ArrayList<>();
        List<String> artistIds = new ArrayList<>();
        List<String> artistNames = new ArrayList<>();
        while (artistMatcher.find()) {
            artistStarts.add(artistMatcher.start());
            artistNames.add(artistMatcher.group(1));
            artistIds.add(artistMatcher.group(2));
        }

        // correlate each track URI with its preceding artist matches
        Matcher trackMatcher = TRACK_URI_PATTERN.matcher(body);
        int artistIdx = 0;
        while (trackMatcher.find()) {
            String trackId = trackMatcher.group(1);
            int trackUriPos = trackMatcher.start();

            List<String> trackArtistIds = new ArrayList<>();
            List<String> trackArtistNames = new ArrayList<>();
            while (artistIdx < artistStarts.size() && artistStarts.get(artistIdx) < trackUriPos) {
                trackArtistIds.add(artistIds.get(artistIdx));
                trackArtistNames.add(artistNames.get(artistIdx));
                artistIdx++;
            }

            trackIds.add(trackId);
            songs.add(new Song(trackId, null, trackArtistIds, trackArtistNames));
        }

        return SpotifyResponseParser.hasMatch(body, NEXT_PATTERN);
    }
}
