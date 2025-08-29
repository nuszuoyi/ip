package command;

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
        case DEADLINE:
        case EVENT:
            addTask(command);
            break;
        case DELETE:
            deleteTask(command);
            break;
        case FIND:
            if (command.args.length == 0) {
                ui.printMessage("OOPS!!! The find command requires a keyword.");
                break;
            }
            String keyword = String.join(" ", command.args); 
            ui.showMatchingTasks(tasks.findTasks(keyword));
            break;
        case BYE:
            ui.printMessage("Bye. Hope to see you again soon!");
            break;
        case INVALID:
            
            ui.printMessage("Sorry, I didn't understand that command. Please try again!");
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
        try {
            int index = Integer.parseInt(command.args[0]) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task task = tasks.get(index);
                if (done) {
                    task.markAsDone();
                    ui.printMessage(String.format(
                        "Nice! I've marked this task as done:\n  [X] %s",
                        task.getDescription()));
                } else {
                    task.markAsUndone();
                    ui.printMessage(String.format(
                        "OK, I've marked this task as not done yet:\n  [ ] %s",
                        task.getDescription()));
                }
                storage.saveTasks(tasks.getTasks());
            } else {
                ui.printMessage("Sorry, that task number does not exist.");
            }
        } catch (Exception e) {
            ui.printMessage("Please enter a valid task number.");
        }
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
}
