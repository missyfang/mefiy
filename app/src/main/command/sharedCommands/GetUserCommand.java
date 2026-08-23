package main.command.sharedCommands;

import main.command.ICommand;
import main.command.commandContext.ISpotifyContext;
import main.command.SpotifyApiClient;
import main.command.SpotifyResponseParser;
import main.command.commandContext.ICommandContext;

import java.util.regex.Pattern;

public class GetUserCommand implements ICommand {

    private static final String URL = "https://api.spotify.com/v1/me";
    public static final Pattern USER_ID_PATTERN = Pattern.compile("\"id\":\"([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        ISpotifyContext ctx = (ISpotifyContext) context;
        try {
            return handleResponse(SpotifyApiClient.get(URL, ctx.getAccessToken()), ctx);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean handleResponse(String body, ISpotifyContext ctx) {
        String userId = SpotifyResponseParser.parseFirst(body, USER_ID_PATTERN);
        if (userId == null) return false;
        ctx.setUserId(userId);
        return true;
    }
}
