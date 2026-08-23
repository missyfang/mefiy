package main.command.sharedCommands;

import main.command.ICommand;
import main.command.commandContext.ICommandContext;
import main.command.commandContext.IPlaylistBuildableContext;
import main.SpotifyResponseParser;

import java.util.regex.Pattern;

// creates an empty playlist
public class CreatePlaylistCommand implements ICommand {

    private static final String URL_PREFIX = "https://api.spotify.com/v1/users/";
    private static final String URL_SUFFIX = "/playlists";
    static final Pattern PLAYLIST_ID_PATTERN = Pattern.compile("\"id\":\"([^\"]+)\"");

    @Override
    public boolean execute(ICommandContext context) {
        IPlaylistBuildableContext ctx = (IPlaylistBuildableContext) context;
        try {
            String url = URL_PREFIX + ctx.getUserId() + URL_SUFFIX;
            String body = "{\"name\":\"" + escapeJson(ctx.getPlaylistName()) + "\",\"public\":false}";
            String response = ctx.getApiClient().post(url, body, ctx.getAccessToken());
            return handleResponse(response, ctx);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean handleResponse(String response, IPlaylistBuildableContext ctx) {
        String playlistId = SpotifyResponseParser.parseFirst(response, PLAYLIST_ID_PATTERN);
        if (playlistId == null) return false;
        ctx.setPlaylistId(playlistId);
        return true;
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
