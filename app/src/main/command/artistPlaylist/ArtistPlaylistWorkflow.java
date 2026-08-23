package main.command.artistPlaylist;

import main.command.CommandWorkflow;
import main.command.CommandWorkflowInvoker;
import main.command.sharedCommands.AddSongsToPlaylistCommand;
import main.command.sharedCommands.CreatePlaylistCommand;
import main.command.sharedCommands.GetLikedSongsCommand;
import main.command.sharedCommands.GetUserCommand;

// workflow for building a playlist from liked songs filtered by a list of artists
public class ArtistPlaylistWorkflow extends CommandWorkflow {

    @Override
    protected void buildCommands(CommandWorkflowInvoker invoker) {
        invoker.addCommand(new GetUserCommand());
        invoker.addCommand(new GetLikedSongsCommand());
        invoker.addCommand(new FilterSongsByArtistListCommand());
        invoker.addCommand(new CreatePlaylistCommand());
        invoker.addCommand(new AddSongsToPlaylistCommand());
    }
}
