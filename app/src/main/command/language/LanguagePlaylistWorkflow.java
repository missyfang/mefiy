package main.command.language;

import main.command.CommandWorkflow;
import main.command.CommandWorkflowInvoker;
import main.command.sharedCommands.GetLikedSongsCommand;
import main.command.sharedCommands.GetUserCommand;

// workflow for building a playlist from liked songs filtered by language
public class LanguagePlaylistWorkflow extends CommandWorkflow {

    @Override
    protected void buildCommands(CommandWorkflowInvoker invoker) {
        invoker.addCommand(new GetUserCommand());
        invoker.addCommand(new GetLikedSongsCommand());
        invoker.addCommand(new FilterSongsByLanguageCommand(new LinguaLanguageDetector()));
    }
}
