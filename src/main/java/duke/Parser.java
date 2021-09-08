package duke;

import duke.commands.AddTaskCommand;
import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.FindTasksCommand;
import duke.commands.ListCommand;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Todo;

/**
 * This class represents a Parser used to parse user input and return Commands that can be executed by Duke.
 */
public class Parser {
    /**
     * Parses user input into a Command that should be executed by the Duke chatbot.
     *
     * @param answer User input parsed into a String.
     * @return Command that is to be executed by Duke based on user input.
     * @throws DukeException If user input is not in the correct format or not a recognised command.
     */
    public static Command parse(String answer) throws DukeException {
        if (answer == null) {
            throw new DukeException("User input is null.");
        }
        
        String[] parts = answer.split(" ");
        String command = parts[0];
        String taskDetails = "";
        if (answer.contains(" ")) {
            taskDetails = answer.substring(answer.indexOf(" ") + 1);
        }
        switch (command) {
        case "done":
            int taskIndex = getTaskIndex(answer);
            return new DoneCommand(taskIndex);
        case "delete":
            taskIndex = getTaskIndex(answer);
            return new DeleteCommand(taskIndex);
        case "list":
            return new ListCommand();
        case "todo":
            Todo todo = parseTodo(taskDetails);
            return new AddTaskCommand(todo);
        case "event":
            Event event = parseEvent(taskDetails);
            return new AddTaskCommand(event);
        case "deadline":
            Deadline deadline = parseDeadline(taskDetails);
            return new AddTaskCommand(deadline);
        case "find":
            String keyword = parseKeyword(taskDetails);
            return new FindTasksCommand(keyword);
        case "bye":
            return new ByeCommand();
        default:
            throw new DukeException("Unknown command.");
        }
    }

    private static String parseKeyword(String taskDetails) throws DukeException {
        if (taskDetails.isEmpty()) {
            throw new DukeException("Keyword for find command cannot be empty");
        }
        return taskDetails;
    }

    private static void checkEmptyTaskDetails(String taskDetails) throws DukeException {
        if (taskDetails.isEmpty()) {
            throw new DukeException("Task details cannot be empty");
        }
    }

    private static Todo parseTodo(String taskDetails) throws DukeException {
        checkEmptyTaskDetails(taskDetails);
        return new Todo(taskDetails);
    }

    private static Event parseEvent(String taskDetails) throws DukeException {
        checkEmptyTaskDetails(taskDetails);
        String[] parts = taskDetails.split(" /at ");
        if (parts.length < 2) {
            throw new DukeException("Event descriptions must contain /at [dd-mm-yyyy hh:mm]");
        }
        String description = parts[0];
        String at = parts[1];
        return new Event(description, at);
    }

    private static Deadline parseDeadline(String taskDetails) throws DukeException {
        checkEmptyTaskDetails(taskDetails);
        String[] parts = taskDetails.split(" /by ");
        if (parts.length < 2) {
            throw new DukeException("Deadline descriptions must contain /by [dd-mm-yyyy hh:mm]");
        }
        String description = parts[0];
        String by = parts[1];
        return new Deadline(description, by);
    }

    protected static int getTaskIndex(String answer) throws DukeException {
        String taskNo = answer.substring(answer.indexOf(" ") + 1);
        try {
            int taskIndex = Integer.parseInt(taskNo) - 1;
            if (taskIndex < 0) {
                throw new DukeException("Invalid task number. Task number should be positive.");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid task number. Sample input with correct format: [command] [taskNo]"
                    + " eg. 'done 2'");
        }
    }
}
