import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a task description and a due date in which
 * the task should be completed by. Users can mark the task as done or undone.
 */
public class Deadline extends Task {
    private LocalDate endDate;

    public Deadline(String name, String endDate) {
        super(name);
        this.endDate = LocalDate.parse(endDate);
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description +
                " | " + endDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " +
                this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
