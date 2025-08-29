package task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Deadline extends Task {
    LocalDate by;

    public Deadline(String description, String byStr) {
        super(description, TaskType.DEADLINE);
        try {
            this.by = LocalDate.parse(byStr.trim());
        } catch (DateTimeParseException e) {
            this.by = null; 
        }
    }

    
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    public String getByRaw() {
        return by != null ? by.toString() : "";
    }

    @Override
    public String toString() {
        String byDisplay = by != null
            ? by.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
            : "invalid date";
        return "[D]" + super.toString() + " (by: " + byDisplay + ")";
    }
}
