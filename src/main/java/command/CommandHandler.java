package command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import parser.Parser;
import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

/**
 * Handles commands from the user and performs operations on the task list.
 * Supports adding, deleting, marking/unmarking, and listing tasks.
 */
public class CommandHandler {
    /** Task list to be managed by this command handler */
    private final TaskList tasks;

    /** Storage for persisting tasks */
    private final Storage storage;

    /** User interface for displaying messages */
    private final Ui ui;

    /**
     * Constructs a CommandHandler with the given task list, storage, and UI.
     *
     * @param tasks Task list to manage.
     * @param storage Storage for saving tasks.
     * @param ui UI for interacting with the user.
     */
    public CommandHandler(TaskList tasks, Storage storage, Ui ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Handles the given command by performing the corresponding action.
     *
     * @param command Command to be executed.
     */
    public void handleCommand(Parser.Command command) {
        switch (command.type) {
        case LIST:
            printTaskList();
            break;
        case MARK:
            markTask(command, true);
            break;
        case UNMARK:
            markTask(command, false);
            break;
        case TODO:
            addTask(command);
            break;
        case DEADLINE:
            if(timeCheckDeadline(command)) {
                addTask(command);
            }
            break;
        case EVENT:
            if(timeCheckEvent(command)) {
                addTask(command);
            }
            break;
        case DELETE:
            deleteTask(command);
            break;
        case FIND:
            handleFindCommand(command);
            break;
        case BYE:
            ui.printMessage("Bye. Hope to see you again soon!");
            break;
        case INVALID:
            ui.printMessage("Sorry, I didn't understand that command. Please try again!");
            break;
        case HELP:
            showHelp();
            break;
        default:
            // do nothing
            break;
        }
    }

    /**
     * Prints all tasks in the task list to the UI.
     */
    private void printTaskList() {
        StringBuilder output = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            output.append(i + 1).append(". ").append(task).append("\n");
        }
        ui.printMessage(output.toString());
    }

    /**
     * Marks or unmarks a task as done based on the specified flag.
     *
     * @param command Command containing the task index.
     * @param done True to mark as done, false to mark as not done.
     */
    private void markTask(Parser.Command command, boolean done) {
        int index;
        try {
            index = Integer.parseInt(command.args[0]) - 1;
        } catch (NumberFormatException e) {
            ui.printMessage("Please enter a valid task number.");
            return;
        }

        if (!isValidTaskIndex(index)) {
            ui.printMessage("Sorry, that task number does not exist.");
            return;
        }

        updateTaskStatus(index, done);
    }

    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    private void updateTaskStatus(int index, boolean done) {
        Task task = tasks.get(index);
        if (done) {
            task.markAsDone();
            ui.printMessage(formatTaskMessage(task, true));
        } else {
            task.markAsUndone();
            ui.printMessage(formatTaskMessage(task, false));
        }
        storage.saveTasks(tasks.getTasks());
    }

    private String formatTaskMessage(Task task, boolean done) {
        return done
            ? String.format("Nice! I've marked this task as done:\n  [X] %s", task.getDescription())
            : String.format("OK, I've marked this task as not done yet:\n  [ ] %s", task.getDescription());
    }

    private boolean timeCheckDeadline(Parser.Command command) {
        assert command.type == Parser.CommandType.DEADLINE;
        
        String by = command.args[1];
        
        try {
            LocalDate.parse(by); 
        } catch (DateTimeParseException e) {
            ui.printMessage("Invalid date format for deadline. Please use YYYY-MM-DD.");
            return false;
        }
        return true;
    }

    private boolean timeCheckEvent(Parser.Command command) {
        assert command.type == Parser.CommandType.EVENT;
        
        
        String from = command.args[1];
        String to = command.args[2];
        
        LocalDate fromDate;
        LocalDate toDate;

        try {
            fromDate = LocalDate.parse(from);
        } catch (DateTimeParseException e) {
            ui.printMessage("Invalid start date for event. Expected yyyy-MM-dd.");
            return false;
        }

        try {
            toDate = LocalDate.parse(to);
        } catch (DateTimeParseException e) {
            ui.printMessage("Invalid end date for event. Expected yyyy-MM-dd.");
            return false;
        }

        if (toDate.isBefore(fromDate)) {
            ui.printMessage("End date cannot be before start date for an event.");
            return false;
        }
        return true;
    }


    /**
     * Adds a new task to the task list based on the command.
     *
     * @param command Command containing task details.
     */
    private void addTask(Parser.Command command) {
        Task newTask = Parser.createTask(command);
        if (newTask != null) {
            tasks.add(newTask);
            storage.saveTasks(tasks.getTasks());
            ui.printMessage(String.format(
                "Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newTask, tasks.size()));
        } else {
            ui.printMessage("Invalid task format.");
        }
    }

    /**
     * Deletes a task from the task list based on the command.
     *
     * @param command Command containing the task index.
     */
    private void deleteTask(Parser.Command command) {
        try {
            int index = Integer.parseInt(command.args[0]) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task removed = tasks.remove(index);
                storage.saveTasks(tasks.getTasks());
                ui.printMessage(String.format(
                    "Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                    removed, tasks.size()));
            } else {
                ui.printMessage("Sorry, that task number does not exist.");
            }
        } catch (Exception e) {
            ui.printMessage("Please enter a valid task number to delete.");
        }
    }

    private void handleFindCommand(Parser.Command command) {
        if (command.args.length == 0) {
            ui.printMessage("OOPS!!! The find command requires a keyword.");
            return;
        }

        String keyword = String.join(" ", command.args);
        ui.showMatchingTasks(tasks.findTasks(keyword));
    }

    private void showHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("Amadeus Help:\n");
        sb.append("Available commands:\n");
        sb.append("1. list               - Show all tasks\n");
        sb.append("2. mark <num>         - Mark a task as done\n");
        sb.append("3. unmark <num>       - Mark a task as not done\n");
        sb.append("4. todo <desc>        - Add a ToDo task\n");
        sb.append("5. deadline <desc> /by <date> - Add a Deadline task\n");
        sb.append("6. event <desc> /from <start> /to <end> - Add an Event task\n");
        sb.append("7. delete <num>       - Delete a task\n");
        sb.append("8. find <keyword>     - Search tasks\n");
        sb.append("9. bye                - Exit\n");
        sb.append("10. help              - Show this help message\n");

        ui.printMessage(sb.toString());
    }

}
