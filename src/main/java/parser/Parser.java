package AmadeusK;

import task.Task;
import task.ToDo;
import task.Deadline;
import task.Event;

public class Parser {

    public enum CommandType {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, INVALID
    }

    public static class Command {
        public final CommandType type;
        public final String[] args;

        public Command(CommandType type, String[] args) {
            this.type = type;
            this.args = args;
        }
    }

    /** 
     * 解析用户输入，返回 Command 对象
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
        } else {
            return new Command(CommandType.INVALID, new String[]{});
        }
    }

    /**
     * 方便直接生成 Task 对象
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
