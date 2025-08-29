package task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Event extends Task {
    LocalDate from;
    LocalDate to;

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

    public Event(String description, LocalDate from, LocalDate to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    public String getFromRaw() {
        return from != null ? from.toString() : "";
    }

    public String getToRaw() {
        return to != null ? to.toString() : "";
    }

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

