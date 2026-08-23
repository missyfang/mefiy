package main.command;

import java.util.List;

public class Song {
    public final String id;
    public final double energy;
    public final double valence;
    public final String lyrics;       // null until fetched
    public final List<String> artistIds; // Spotify artist IDs, populated by GetSongAttributesCommand

    public Song(String id, double energy, double valence) {
        this(id, energy, valence, null, List.of());
    }

    public Song(String id, double energy, double valence, String lyrics) {
        this(id, energy, valence, lyrics, List.of());
    }

    public Song(String id, double energy, double valence, String lyrics, List<String> artistIds) {
        this.id = id;
        this.energy = energy;
        this.valence = valence;
        this.lyrics = lyrics;
        this.artistIds = List.copyOf(artistIds);
    }
}
