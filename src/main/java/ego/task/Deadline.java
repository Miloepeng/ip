package ego.task;

import ego.exception.EgoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a ego.task.Deadline task with a task description and a due date in which
 * the task should be completed by. Users can mark the task as done or undone.
 */
public class Deadline extends Task {
    private LocalDate endDate;

    public Deadline(String name, String endDate) throws EgoException {
        super(name);
        try {
            this.endDate = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new EgoException("Hey there! Please enter the date in yyyy-MM-dd format!, eg. 2025-08-25");
        }
    }

    public Deadline(String description, LocalDate endDate) {
        super(description);
        this.endDate = endDate;
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
