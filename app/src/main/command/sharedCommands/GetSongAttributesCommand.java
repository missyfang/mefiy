package main.command.sharedCommands;

import main.command.*;
import main.command.commandContext.ICommandContext;
import main.command.commandContext.ISpotifyContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// fetches energy, valence, and artist IDs for each liked track in one command (two endpoints per batch)
public class GetSongAttributesCommand implements ICommand {

    private static final String AUDIO_FEATURES_URL = "https://api.spotify.com/v1/audio-features?ids=";
    private static final String TRACKS_URL = "https://api.spotify.com/v1/tracks?ids=";
    // /v1/tracks allows max 50 ids; use that as the shared batch size
    private static final int BATCH_SIZE = 50;

    static final Pattern ENERGY_PATTERN = Pattern.compile("\"energy\":(\\d+\\.?\\d*)");
    static final Pattern VALENCE_PATTERN = Pattern.compile("\"valence\":(\\d+\\.?\\d*)");

    // artist URIs are unique to artist objects; track URIs are unique to track objects
    static final Pattern ARTIST_URI_PATTERN = Pattern.compile("\"uri\":\"spotify:artist:([^\"]+)\"");
    static final Pattern TRACK_URI_PATTERN  = Pattern.compile("\"uri\":\"spotify:track:([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        ISpotifyContext ctx = (ISpotifyContext) context;
        List<Song> songs = new ArrayList<>();
        try {
            for (List<String> batch : partition(ctx.getLikedTrackIds(), BATCH_SIZE)) {
                String ids = String.join(",", batch);
                String audioFeaturesBody = SpotifyApiClient.get(AUDIO_FEATURES_URL + ids, ctx.getAccessToken());
                String tracksBody        = SpotifyApiClient.get(TRACKS_URL + ids, ctx.getAccessToken());
                handleResponse(audioFeaturesBody, tracksBody, batch, songs);
            }
            ctx.setSongs(songs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void handleResponse(String audioFeaturesBody, String tracksBody, List<String> batchIds, List<Song> songs) {
        // energy/valence — Spotify preserves request order so index i maps to batchIds[i]
        List<String> energyStrs  = SpotifyResponseParser.parseAll(audioFeaturesBody, ENERGY_PATTERN);
        List<String> valenceStrs = SpotifyResponseParser.parseAll(audioFeaturesBody, VALENCE_PATTERN);

        // artist IDs per track — artist URIs appear before the track's own URI in the JSON
        // (Spotify serialises fields alphabetically: "artists" < "uri"), so we use positional correlation
        Matcher artistMatcher = ARTIST_URI_PATTERN.matcher(tracksBody);
        List<Integer> artistStarts = new ArrayList<>();
        List<String>  artistIds    = new ArrayList<>();
        while (artistMatcher.find()) {
            artistStarts.add(artistMatcher.start());
            artistIds.add(artistMatcher.group(1));
        }

        Matcher trackMatcher = TRACK_URI_PATTERN.matcher(tracksBody);
        List<List<String>> artistsPerTrack = new ArrayList<>();
        int artistIdx = 0;
        while (trackMatcher.find()) {
            int trackUriPos = trackMatcher.start();
            List<String> trackArtists = new ArrayList<>();
            while (artistIdx < artistStarts.size() && artistStarts.get(artistIdx) < trackUriPos) {
                trackArtists.add(artistIds.get(artistIdx));
                artistIdx++;
            }
            artistsPerTrack.add(trackArtists);
        }

        for (int i = 0; i < batchIds.size(); i++) {
            double energy  = i < energyStrs.size()  ? Double.parseDouble(energyStrs.get(i))  : 0.0;
            double valence = i < valenceStrs.size()  ? Double.parseDouble(valenceStrs.get(i)) : 0.0;
            List<String> trackArtistIds = i < artistsPerTrack.size() ? artistsPerTrack.get(i) : List.of();
            songs.add(new Song(batchIds.get(i), energy, valence, null, trackArtistIds));
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
