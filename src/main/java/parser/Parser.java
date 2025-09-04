package parser;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

/**
 * Parses user input into commands and provides convenient Task creation.
 */
public class Parser {

    /** Enum representing all possible command types */
    public enum CommandType {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, BYE, INVALID
    }

    /**
     * Represents a parsed user command.
     * Contains the command type and associated arguments.
     */
    public static class Command {
        public final CommandType type;
        public final String[] args;

        /**
         * Constructs a Command with the specified type and arguments.
         *
         * @param type Type of the command.
         * @param args Arguments associated with the command.
         */
        public Command(CommandType type, String[] args) {
            this.type = type;
            this.args = args;
        }
    }

    /**
     * Parses the user input string and returns a corresponding Command object.
     *
     * @param input User input string.
     * @return Parsed Command object; returns CommandType.INVALID if input is invalid.
     */
    public static Command parse(String input) {
        if (input.equals("list")) {
            return new Command(CommandType.LIST, new String[]{});
        } else if (input.startsWith("mark")) {
            return new Command(CommandType.MARK, new String[]{input.split(" ")[1]});
        } else if (input.startsWith("unmark")) {
            return new Command(CommandType.UNMARK, new String[]{input.split(" ")[1]});
        } else if (input.startsWith("todo")) {
            return new Command(CommandType.TODO, new String[]{input.substring(5)});
        } else if (input.startsWith("deadline")) {
            String[] parts = input.substring(9).split(" /by ");
            if (parts.length == 2) {
                return new Command(CommandType.DEADLINE, parts);
            } else {
                return new Command(CommandType.INVALID, new String[]{});
            }
        } else if (input.startsWith("event")) {
            String[] parts1 = input.substring(6).split(" /from ");
            if (parts1.length == 2) {
                String[] parts2 = parts1[1].split(" /to ");
                if (parts2.length == 2) {
                    return new Command(CommandType.EVENT, new String[]{parts1[0], parts2[0], parts2[1]});
                }
            }
            return new Command(CommandType.INVALID, new String[]{});
        } else if (input.startsWith("delete")) {
            return new Command(CommandType.DELETE, new String[]{input.split(" ")[1]});
        } else if (input.equals("bye")) {
            return new Command(CommandType.BYE, new String[]{});
        } else if (input.startsWith("find")) {
            String keyword = input.substring(5).trim();
            return new Command(CommandType.FIND, new String[]{keyword});
        } else {
            return new Command(CommandType.INVALID, new String[]{});
        }
    }

    /**
     * Generates a Task object from a given Command object.
     *
     * @param command Command from which to generate the Task.
     * @return Task object corresponding to the command; returns null for unsupported commands.
     */
    public static Task createTask(Command command) {
        switch (command.type) {
        case TODO:
            return new ToDo(command.args[0]);
        case DEADLINE:
            return new Deadline(command.args[0], command.args[1]);
        case EVENT:
            return new Event(command.args[0], command.args[1], command.args[2]);
        default:
            return null;
        }
    }
}
