package main;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class main {
    public static void main(String[] args) throws Exception {
        AppConfig config = new AppConfig();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new IndexHandler());
        server.createContext("/home", new HomeHandler());
        server.createContext("/token", new FetchToken(config)::handle);

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Running at http://localhost:8080");
    }
}
