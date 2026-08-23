package main.command.GetLikeArtistInfo;

import main.command.*;
import main.command.sharedCommands.GetLikedSongsCommand;
import main.command.sharedCommands.GetUserCommand;

// workflow for getting artist info from liked songs
public class LikedArtistInfoWorkflow extends CommandWorkflow {

    @Override
    protected void buildCommands(CommandWorkflowInvoker invoker) {
        invoker.addCommand(new GetUserCommand());
        invoker.addCommand(new GetLikedSongsCommand());
        invoker.addCommand(new GetArtistInfosCommand());
    }
}