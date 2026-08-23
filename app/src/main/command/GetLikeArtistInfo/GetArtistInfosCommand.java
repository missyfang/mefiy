package main.command.GetLikeArtistInfo;

import main.command.*;
import main.command.commandContext.ICommandContext;
import main.command.commandContext.ISpotifyContext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// collects unique artist IDs from songs, calls /v1/artists?ids=, and populates the context with Artist objects
public class GetArtistInfosCommand implements ICommand {

    private static final String ARTISTS_URL = "https://api.spotify.com/v1/artists?ids=";
    private static final int BATCH_SIZE = 50;

    // each artist object in the response has these fields (alphabetical field order in Spotify JSON):
    // "genres":[...], "name":"...", "popularity":N, "uri":"spotify:artist:..."
    static final Pattern GENRE_PATTERN = Pattern.compile("\"genres\":\\[([^\\]]*)\\]");
    static final Pattern NAME_PATTERN = Pattern.compile("\"name\":\"([^\"]+)\"");
    static final Pattern POPULARITY_PATTERN = Pattern.compile("\"popularity\":(\\d+)");
    static final Pattern ARTIST_URI_PATTERN = Pattern.compile("\"uri\":\"spotify:artist:([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        LikedArtistInfoContext ctx = (LikedArtistInfoContext) context;
        ISpotifyContext spotifyCtx = (ISpotifyContext) context;

        Set<String> seen = new LinkedHashSet<>();
        for (Song song : spotifyCtx.getSongs()) {
            seen.addAll(song.artistIds);
        }
        List<String> uniqueArtistIds = new ArrayList<>(seen);

        List<Artist> artists = new ArrayList<>();
        try {
            for (List<String> batch : partition(uniqueArtistIds, BATCH_SIZE)) {
                String ids = String.join(",", batch);
                String body = SpotifyApiClient.get(ARTISTS_URL + ids, spotifyCtx.getAccessToken());
                handleResponse(body, artists);
            }
            ctx.setArtists(artists);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleResponse(String body, List<Artist> artists) {
        List<String> ids = SpotifyResponseParser.parseAll(body, ARTIST_URI_PATTERN);
        List<String> names = SpotifyResponseParser.parseAll(body, NAME_PATTERN);
        List<String> popularities = SpotifyResponseParser.parseAll(body, POPULARITY_PATTERN);
        List<List<String>> genresPerArtist = parseGenres(body);

        for (int i = 0; i < ids.size(); i++) {
            String name = i < names.size() ? names.get(i) : "Unknown";
            int popularity = i < popularities.size() ? Integer.parseInt(popularities.get(i)) : 0;
            List<String> genres = i < genresPerArtist.size() ? genresPerArtist.get(i) : List.of();
            artists.add(new Artist(ids.get(i), name, genres, popularity));
        }
    }

    private List<List<String>> parseGenres(String body) {
        List<List<String>> result = new ArrayList<>();
        Matcher matcher = GENRE_PATTERN.matcher(body);
        Pattern singleGenre = Pattern.compile("\"([^\"]+)\"");
        while (matcher.find()) {
            String genreArrayContent = matcher.group(1);
            List<String> genres = new ArrayList<>();
            Matcher gm = singleGenre.matcher(genreArrayContent);
            while (gm.find()) {
                genres.add(gm.group(1));
            }
            result.add(genres);
        }
        return result;
    }

    private static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            batches.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return batches;
    }
}
