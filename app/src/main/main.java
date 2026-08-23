package main;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class main {
    public static void main(String[] args) throws Exception {
        // get global configs
        AppConfig config = new AppConfig();

        // create server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // handle html page serving
        server.createContext("/", new IndexPageServer());
        server.createContext("/home", new HomePageServer());
        server.createContext("/feature", new FeaturePageServer());
        server.createContext("/liked-artists", new LikedArtistsPageServer());
        server.createContext("/artist-mood", new ArtistMoodPageServer());
        server.createContext("/language-playlist", new LanguagePlaylistPageServer());

        // configure EPs
        server.createContext("/token", new FetchToken(config)::handle);
        server.createContext("/api/liked-artists", new FetchLikedArtists()::handle);
        server.createContext("/api/artist-playlist", new FetchArtistPlaylist()::handle);

        server.setExecutor(Executors.newCachedThreadPool());

        // start server
        server.start();
        System.out.println("Running at http://localhost:8080");
    }
}
