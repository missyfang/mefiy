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
    @Override
    protected String getFilename() { return "index.html"; }
}

class HomePageServer extends HtmlPageServer {
    @Override
    protected String getFilename() { return "home.html"; }
}
