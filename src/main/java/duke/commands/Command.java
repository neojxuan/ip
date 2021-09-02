package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a Command to be executed by the Duke program.
 */
public abstract class Command {
    /**
     * Executes a Command.
     *
     * @param tasks TaskList containing tasks on which Command can be executed.
     * @param ui Ui to show messages to user when Command is executed.
     * @param storage Storage where tasks should be stored.
     * @throws DukeException
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    /**
     * Represents if Duke program should be exited. Should be overridden by Command that exits program.
     *
     * @return False.
     */
    public boolean isExit() {
        return false;
    }
}
