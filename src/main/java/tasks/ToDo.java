package tasks;

/**
 * Represents a ToDo task with only a description and no date.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the specified description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     * Returns a string representation of the ToDo task.
     *
     * @return Formatted string including type and description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
