package main.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

// fetches energy and valence for each liked track and builds Song objects
public class GetSongAttributesCommand implements ICommand {

    private static final String BASE_URL = "https://api.spotify.com/v1/audio-features?ids=";
    private static final int BATCH_SIZE = 100;

    // Spotify preserves request order in the response, so energyStrs[i]/valenceStrs[i] = batchIds[i]
    static final Pattern ENERGY_PATTERN = Pattern.compile("\"energy\":(\\d+\\.?\\d*)");
    static final Pattern VALENCE_PATTERN = Pattern.compile("\"valence\":(\\d+\\.?\\d*)");

    @Override
    public boolean execute(ICommandContext context) {
        LanguagePlaylistContext ctx = (LanguagePlaylistContext) context;
        List<Song> songs = new ArrayList<>();
        try {
            for (List<String> batch : partition(ctx.likedTrackIds, BATCH_SIZE)) {
                String url = BASE_URL + String.join(",", batch);
                handleResponse(SpotifyApiClient.get(url, ctx.accessToken), batch, songs);
            }
            ctx.songs = songs;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void handleResponse(String body, List<String> batchIds, List<Song> songs) {
        List<String> energyStrs = SpotifyResponseParser.parseAll(body, ENERGY_PATTERN);
        List<String> valenceStrs = SpotifyResponseParser.parseAll(body, VALENCE_PATTERN);

        for (int i = 0; i < batchIds.size(); i++) {
            double energy = i < energyStrs.size() ? Double.parseDouble(energyStrs.get(i)) : 0.0;
            double valence = i < valenceStrs.size() ? Double.parseDouble(valenceStrs.get(i)) : 0.0;
            songs.add(new Song(batchIds.get(i), energy, valence));
        }
    }

    private static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            batches.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return batches;
    }
}
