package main;

public interface ISpotifyApiClient {
    String get(String url, String token) throws Exception;
    String post(String url, String jsonBody, String token) throws Exception;
}
