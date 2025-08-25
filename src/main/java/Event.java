import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event task with a task description alongside the time period the user
 * should complete the task in. Users can also mark the task as done or undone.
 */
public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String name, String startDate, String endDate) throws EgoException {
        super(name);
        try {
            this.startDate = LocalDate.parse(startDate);
            this.endDate = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new EgoException("Hey there! Please enter the date in yyyy-MM-dd format!, eg. 2025-08-25");
        }
    }

    public Event(String description, LocalDate startDate, LocalDate endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toFileFormat() {
        return "E | "+ (isDone ? "1" : "0") + " | " + description +
                " | " + startDate + " | " + endDate;
    }

    @Override
    public String toString() {
        String duration = "(from: " +
                this.startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " to: "
                + this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))+ ")";
        return "[E]" + super.toString() + duration;
    }
}
