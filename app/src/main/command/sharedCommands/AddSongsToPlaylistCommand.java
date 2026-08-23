package main.command.sharedCommands;

import main.command.ICommand;
import main.command.commandContext.ICommandContext;
import main.command.commandContext.IPlaylistBuildableContext;
import main.models.Song;

import java.util.List;

// adds songs to a playlist
public class AddSongsToPlaylistCommand implements ICommand {

    private static final String URL_PREFIX = "https://api.spotify.com/v1/playlists/";
    private static final String URL_SUFFIX = "/tracks";
    private static final int BATCH_SIZE = 100;

    @Override
    public boolean execute(ICommandContext context) {
        IPlaylistBuildableContext ctx = (IPlaylistBuildableContext) context;
        String url = URL_PREFIX + ctx.getPlaylistId() + URL_SUFFIX;

        try {
            List<Song> songs = ctx.getPlaylistSongs();
            for (int i = 0; i < songs.size(); i += BATCH_SIZE) {
                List<Song> batch = songs.subList(i, Math.min(i + BATCH_SIZE, songs.size()));
                StringBuilder sb = new StringBuilder("{\"uris\":[");
                for (int j = 0; j < batch.size(); j++) {
                    if (j > 0) sb.append(",");
                    sb.append("\"spotify:track:").append(batch.get(j).id).append("\"");
                }
                sb.append("]}");
                ctx.getApiClient().post(url, sb.toString(), ctx.getAccessToken());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
