package main.models;

import java.util.List;

public class Song {
    public final String id;
    public final String lyrics;       // null until fetched
    public final List<String> artistIds;   // Spotify artist IDs
    public final List<String> artistNames; // artist names, parallel to artistIds

    public Song(String id) {
        this(id, null, List.of(), List.of());
    }

    public Song(String id, String lyrics, List<String> artistIds) {
        this(id, lyrics, artistIds, List.of());
    }

    public Song(String id, String lyrics, List<String> artistIds, List<String> artistNames) {
        this.id = id;
        this.lyrics = lyrics;
        this.artistIds = List.copyOf(artistIds);
        this.artistNames = List.copyOf(artistNames);
    }
}
