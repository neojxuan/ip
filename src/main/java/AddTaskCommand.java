public class AddTaskCommand extends Command {
    Task task;
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.addNewTask(task);
        ui.showAddTask(tasks, task);
    };
}
