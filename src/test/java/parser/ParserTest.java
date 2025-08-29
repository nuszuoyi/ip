package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

/**
 * Tests the functionality of the Parser class, including command parsing
 * and Task object creation.
 */
public class ParserTest {

    /**
     * Tests parsing of various valid and invalid user commands.
     * Ensures that the returned Command objects have the correct type and arguments.
     */
    @Test
    void testParseCommands() {
        Parser.Command listCmd = Parser.parse("list");
        assertEquals(Parser.CommandType.LIST, listCmd.type);
        assertEquals(0, listCmd.args.length);

        Parser.Command todoCmd = Parser.parse("todo Write code");
        assertEquals(Parser.CommandType.TODO, todoCmd.type);
        assertEquals("Write code", todoCmd.args[0]);

        Parser.Command deadlineCmd = Parser.parse("deadline Submit report /by 2025-08-30");
        assertEquals(Parser.CommandType.DEADLINE, deadlineCmd.type);
        assertEquals("Submit report", deadlineCmd.args[0]);
        assertEquals("2025-08-30", deadlineCmd.args[1]);

        Parser.Command eventCmd = Parser.parse("event Conference /from 2025-09-01 /to 2025-09-03");
        assertEquals(Parser.CommandType.EVENT, eventCmd.type);
        assertEquals("Conference", eventCmd.args[0]);
        assertEquals("2025-09-01", eventCmd.args[1]);
        assertEquals("2025-09-03", eventCmd.args[2]);

        Parser.Command invalidCmd = Parser.parse("unknown command");
        assertEquals(Parser.CommandType.INVALID, invalidCmd.type);
    }

    /**
     * Tests creation of Task objects from Command objects.
     * Ensures that the correct Task subclass is returned and that
     * the description is set correctly.
     */
    @Test
    void testCreateTask() {
        Parser.Command todoCmd = new Parser.Command(Parser.CommandType.TODO, new String[]{"Write tests"});
        Task todo = Parser.createTask(todoCmd);
        assertTrue(todo instanceof ToDo);
        assertEquals("Write tests", todo.getDescription());

        Parser.Command deadlineCmd = new Parser.Command(Parser.CommandType.DEADLINE, new String[]{"Submit report", "2025-08-30"});
        Task deadline = Parser.createTask(deadlineCmd);
        assertTrue(deadline instanceof Deadline);
        assertEquals("Submit report", deadline.getDescription());

        Parser.Command eventCmd = new Parser.Command(Parser.CommandType.EVENT, new String[]{"Conference", "2025-09-01", "2025-09-03"});
        Task event = Parser.createTask(eventCmd);
        assertTrue(event instanceof Event);
        assertEquals("Conference", event.getDescription());
    }
}
