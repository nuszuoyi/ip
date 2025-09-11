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
        input = input.trim();
        if (input.isEmpty()) {
            return new Command(CommandType.INVALID, new String[]{});
        }

        String[] tokens = input.split(" ", 2);  // 第一个单词 + 剩余
        String commandWord = tokens[0];
        String args = tokens.length > 1 ? tokens[1] : "";

        switch (commandWord) {
        case "list":
            return new Command(CommandType.LIST, new String[]{});
        case "mark":
            return parseMarkUnmark(args, CommandType.MARK);
        case "unmark":
            return parseMarkUnmark(args, CommandType.UNMARK);
        case "todo":
            return parseTodo(args);
        case "deadline":
            return parseDeadline(args);
        case "event":
            return parseEvent(args);
        case "delete":
            return parseDelete(args);
        case "bye":
            return new Command(CommandType.BYE, new String[]{});
        case "find":
            return parseFind(args);
        default:
            return new Command(CommandType.INVALID, new String[]{});
        }
    }

    private static Command parseMarkUnmark(String args, CommandType type) {
        if (args.isEmpty()) return new Command(CommandType.INVALID, new String[]{});
        return new Command(type, new String[]{args.trim()});
    }

    private static Command parseTodo(String args) {
        if (args.isEmpty()) return new Command(CommandType.INVALID, new String[]{});
        return new Command(CommandType.TODO, new String[]{args});
    }

    private static Command parseDeadline(String args) {
        String[] parts = args.split(" /by ", 2);
        if (parts.length != 2) return new Command(CommandType.INVALID, new String[]{});
        return new Command(CommandType.DEADLINE, parts);
    }

    private static Command parseEvent(String args) {
        String[] parts1 = args.split(" /from ", 2);
        if (parts1.length != 2) return new Command(CommandType.INVALID, new String[]{});
        String[] parts2 = parts1[1].split(" /to ", 2);
        if (parts2.length != 2) return new Command(CommandType.INVALID, new String[]{});
        return new Command(CommandType.EVENT, new String[]{parts1[0], parts2[0], parts2[1]});
    }

    private static Command parseDelete(String args) {
        if (args.isEmpty()) return new Command(CommandType.INVALID, new String[]{});
        return new Command(CommandType.DELETE, new String[]{args.trim()});
    }

    private static Command parseFind(String args) {
        if (args.isEmpty()) return new Command(CommandType.INVALID, new String[]{});
        return new Command(CommandType.FIND, new String[]{args.trim()});
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
