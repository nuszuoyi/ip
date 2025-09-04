package tasks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Tests the behavior of Task-related classes such as ToDo and Deadline.
 */
public class TaskTest {

    /**
     * Tests marking a Todo task as done.
     */
    @Test
    public void testMarkAsDoneAndUndone() {
        Task t = new ToDo("Test task");
        assertFalse(t.isDone());
        t.markAsDone();
        assertTrue(t.isDone());
        t.markAsUndone();
        assertFalse(t.isDone());
    }
}
