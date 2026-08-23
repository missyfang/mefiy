package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class HtmlPageServer implements HttpHandler {
    protected abstract String getFilename();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] page = getClass().getClassLoader()
                .getResourceAsStream(getFilename()).readAllBytes();
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, page.length);
        exchange.getResponseBody().write(page);
        exchange.getResponseBody().close();
    }
}

class IndexPageServer extends HtmlPageServer {
    private final String filename = "index.html";

    @Override
    protected String getFilename() { return filename; }
}

class HomePageServer extends HtmlPageServer {
    private final String filename = "home.html";

    @Override
    protected String getFilename() { return filename; }
}

class FeaturePageServer extends HtmlPageServer {
    private final String filename = "feature.html";

    @Override
    protected String getFilename() { return filename; }
}

class LikedArtistsPageServer extends HtmlPageServer {
    private final String filename = "liked-artists.html";

    @Override
    protected String getFilename() { return filename; }
}

class ArtistMoodPageServer extends HtmlPageServer {
    private final String filename = "artist-mood.html";

    @Override
    protected String getFilename() { return filename; }
}

class LanguagePlaylistPageServer extends HtmlPageServer {
    private final String filename = "language-playlist.html";

    @Override
    protected String getFilename() { return filename; }
}
