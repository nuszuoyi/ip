package tasks;

/**
 * Represents a generic task with a description, status, and type.
 * This is an abstract class extended by specific task types like ToDo, Deadline, and Event.
 */
public abstract class Task {

    /** Description of the task */
    protected String description;

    /** Whether the task is completed */
    protected boolean isDone;

    /** Type of the task */
    protected TaskType type;

    /**
     * Constructs a Task with the specified description and type.
     * Initializes the task as not done.
     *
     * @param description Description of the task.
     * @param type Type of the task.
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        assert description != null : "Task description should never be null";
        return description;
    }

    /**
     * Returns a status icon representing whether the task is done.
     *
     * @return "X" if done, otherwise a space " ".
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the type of the task.
     *
     * @return TaskType of the task.
     */
    public TaskType getType() {
        assert type != null : "Task type should never be null";
        return type;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return Formatted string including status icon and description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
