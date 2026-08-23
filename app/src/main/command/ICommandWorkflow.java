package main.command;

import main.command.commandContext.ICommandContext;

public interface ICommandWorkflow {
    boolean run(ICommandContext context);
}
