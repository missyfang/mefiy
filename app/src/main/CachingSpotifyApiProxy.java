package main;

import java.util.HashMap;
import java.util.Map;

// proxy that caches GET responses so repeated calls don't hit the Spotify API
public class CachingSpotifyApiProxy implements ISpotifyApiClient {

    private final ISpotifyApiClient realClient;
    private final Map<String, String> cache = new HashMap<>();

    public CachingSpotifyApiProxy(ISpotifyApiClient realClient) {
        this.realClient = realClient;
    }

    @Override
    public String get(String url, String token) throws Exception {
        if (cache.containsKey(url)) {
            return cache.get(url);
        }
        String response = realClient.get(url, token);
        cache.put(url, response);
        return response;
    }

    @Override
    public String post(String url, String jsonBody, String token) throws Exception {
        return realClient.post(url, jsonBody, token);
    }
}
