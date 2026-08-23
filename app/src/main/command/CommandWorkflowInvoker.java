package main.command;

import main.command.commandContext.ICommandContext;

import java.util.ArrayList;
import java.util.List;

// orchestrates a sequence of commands with a shared context
public class CommandWorkflowInvoker {

    private final List<ICommand> commands = new ArrayList<>();

    public CommandWorkflowInvoker addCommand(ICommand command) {
        commands.add(command);
        return this;
    }

    public boolean run(ICommandContext context) {
        for (ICommand command : commands) {
            if (!command.execute(context)) {
                return false;
            }
        }
        return true;
    }
}
