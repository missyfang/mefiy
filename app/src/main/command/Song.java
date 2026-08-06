package main.command;

public class Song {
    public final String id;
    public final double energy;
    public final double valence;
    public final String lyrics; // null until fetched by GetSongLyricsCommand

    public Song(String id, double energy, double valence) {
        this(id, energy, valence, null);
    }

    public Song(String id, double energy, double valence, String lyrics) {
        this.id = id;
        this.energy = energy;
        this.valence = valence;
        // todo idk how to get these for freeeeee
        this.lyrics = lyrics;
    }
}
