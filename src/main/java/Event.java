import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a task description alongside the time period the user
 * should complete the task in. Users can also mark the task as done or undone.
 */
public class Event extends Task{
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
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
