package main.command.language;

import main.command.CommandWorkflow;
import main.command.CommandWorkflowInvoker;
import main.command.sharedCommands.FilterSongsByMoodCommand;
import main.command.sharedCommands.GetLikedSongsCommand;
import main.command.sharedCommands.GetSongAttributesCommand;
import main.command.sharedCommands.GetUserCommand;

// workflow for building a playlist from liked songs filtered by mood and language
public class LanguagePlaylistWorkflow extends CommandWorkflow {

    @Override
    protected void buildCommands(CommandWorkflowInvoker invoker) {
        invoker.addCommand(new GetUserCommand());
        invoker.addCommand(new GetLikedSongsCommand());
        invoker.addCommand(new GetSongAttributesCommand());
        invoker.addCommand(new FilterSongsByMoodCommand());
        invoker.addCommand(new FilterSongsByLanguageCommand(new LinguaLanguageDetector()));
    }
}
