package main.command;

import java.util.List;

public class Song {
    public final String id;
    public final String lyrics;       // null until fetched
    public final List<String> artistIds; // Spotify artist IDs, populated by GetLikedSongsCommand

    public Song(String id) {
        this(id, null, List.of());
    }

    public Song(String id, String lyrics, List<String> artistIds) {
        this.id = id;
        this.lyrics = lyrics;
        this.artistIds = List.copyOf(artistIds);
    }
}
