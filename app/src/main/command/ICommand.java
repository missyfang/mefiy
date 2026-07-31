package main.command;

public interface ICommand {
    boolean execute(ICommandContext context);
}
