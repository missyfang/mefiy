package main.command;

import java.util.regex.Pattern;

public class GetUserCommand implements ICommand {

    private static final String URL = "https://api.spotify.com/v1/me";
    static final Pattern USER_ID_PATTERN = Pattern.compile("\"id\":\"([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        LanguagePlaylistContext ctx = (LanguagePlaylistContext) context;
        try {
            return handleResponse(SpotifyApiClient.get(URL, ctx.accessToken), ctx);
        } catch (Exception e) {
            return false;
        }
    }

    boolean handleResponse(String body, LanguagePlaylistContext ctx) {
        String userId = SpotifyResponseParser.parseFirst(body, USER_ID_PATTERN);
        if (userId == null) return false;
        ctx.userId = userId;
        return true;
    }
}
