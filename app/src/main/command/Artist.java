package main.command;

import java.util.List;

public class Artist {
    public final String id;
    public final String name;
    public final List<String> genres;
    public final int popularity;

    public Artist(String id, String name, List<String> genres, int popularity) {
        this.id = id;
        this.name = name;
        this.genres = List.copyOf(genres);
        this.popularity = popularity;
    }
}
