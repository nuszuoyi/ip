package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents an event task with a start and end date.
 * Stores the description, start date, and end date of the event.
 */
public class Event extends Task {

    /** Start date of the event */
    private LocalDate from;

    /** End date of the event */
    private LocalDate to;

    /**
     * Constructs an Event task with a description and date strings.
     * Attempts to parse the strings into LocalDate objects; sets null if parsing fails.
     *
     * @param description Description of the event.
     * @param fromStr Start date as a string.
     * @param toStr End date as a string.
     */
    public Event(String description, String fromStr, String toStr) {
        super(description, TaskType.EVENT);
        try {
            this.from = LocalDate.parse(fromStr.trim());
        } catch (DateTimeParseException e) {
            this.from = null;
        }
        try {
            this.to = LocalDate.parse(toStr.trim());
        } catch (DateTimeParseException e) {
            this.to = null;
        }
    }

    /**
     * Constructs an Event task with a description and LocalDate objects.
     *
     * @param description Description of the event.
     * @param from Start date as a LocalDate.
     * @param to End date as a LocalDate.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the raw string representation of the start date.
     *
     * @return String representation of the start date, or empty string if invalid.
     */
    public String getFromRaw() {
        return from != null ? from.toString() : "";
    }

    /**
     * Returns the raw string representation of the end date.
     *
     * @return String representation of the end date, or empty string if invalid.
     */
    public String getToRaw() {
        return to != null ? to.toString() : "";
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return Formatted string including type, description, start date, and end date.
     */
    @Override
    public String toString() {
        String fromDisplay = from != null
            ? from.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
            : "invalid date";
        String toDisplay = to != null
            ? to.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
            : "invalid date";
        return "[E]" + super.toString() + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}
