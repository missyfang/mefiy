package main.command;

import main.command.commandContext.ICommandContext;

public interface ICommand {
    boolean execute(ICommandContext context);
}
