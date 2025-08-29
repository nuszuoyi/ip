package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task with a deadline.
 * Stores the description and the due date of the task.
 */
public class Deadline extends Task {

    /** Deadline date of the task */
    private LocalDate by;

    /**
     * Constructs a Deadline task with a description and a date string.
     * Attempts to parse the string into a LocalDate; sets null if parsing fails.
     *
     * @param description Description of the task.
     * @param byStr Due date as a string.
     */
    public Deadline(String description, String byStr) {
        super(description, TaskType.DEADLINE);
        try {
            this.by = LocalDate.parse(byStr.trim());
        } catch (DateTimeParseException e) {
            this.by = null; 
        }
    }

    /**
     * Constructs a Deadline task with a description and a LocalDate object.
     *
     * @param description Description of the task.
     * @param by Due date as a LocalDate.
     */
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns the raw string representation of the deadline date.
     *
     * @return String representation of the date, or empty string if invalid.
     */
    public String getByRaw() {
        return by != null ? by.toString() : "";
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return Formatted string including type, description, and due date.
     */
    @Override
    public String toString() {
        String byDisplay = by != null
            ? by.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
            : "invalid date";
        return "[D]" + super.toString() + " (by: " + byDisplay + ")";
    }
}
