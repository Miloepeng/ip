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

    /**
     * Constructor method for Deadline class.
     * @param desc The description of the task.
     * @param endDate The date which the task must be completed by as given by the user.
     * @throws EgoException If the format in which the task is being inputted by the user
     * in his command is invalid.
     */
    public Deadline(String desc, String endDate) throws EgoException {
        super(desc);
        try {
            this.endDate = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new EgoException("Hey there! Please enter the date in yyyy-MM-dd format!, eg. 2025-08-25");
        }
    }

    /**
     * Constructor method for Deadline class.
     * @param description The description of the task.
     * @param endDate The date which the task must be completed by, as read from the storage file.
     */
    public Deadline(String description, LocalDate endDate) {
        super(description);
        this.endDate = endDate;
    }

    /**
     * Returns a String representation of the Deadline object in the correct format to be stored in
     * storage.
     * @return A correct String representation of the Deadline object to be stored in storage.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description +
                " | " + endDate;
    }

    /**
     * Returns a String representation of the Deadline object as displayed to user.
     * @return A correct String representation of the Deadline object as displayed to user.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " +
                this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
