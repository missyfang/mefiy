package main.command;

public interface IWorkflowFactory {
    ICommandWorkflow create(String feature);
}
