package main.command;

import main.command.commandContext.ICommandContext;

// abstract base for a predefined command chain — subclasses register their commands in buildCommands()
// uses the Template Method pattern: the execution sequence is fixed here, the commands vary per subclass
public abstract class CommandWorkflow {

    private final CommandWorkflowInvoker invoker = new CommandWorkflowInvoker();

    public CommandWorkflow() {
        buildCommands(invoker);
    }

    // subclasses call invoker.addCommand() to register the steps for their specific workflow
    protected abstract void buildCommands(CommandWorkflowInvoker invoker);

    public boolean run(ICommandContext context) {
        return invoker.run(context);
    }
}
