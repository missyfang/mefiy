package main.command;

public class Song {
    public final String id;
    public final double energy;
    public final double valence;

    public Song(String id, double energy, double valence) {
        this.id = id;
        this.energy = energy;
        this.valence = valence;
    }
}
