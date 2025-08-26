package ego.task;

import ego.exception.EgoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an ego.task.Event task with a task description alongside the time period the user
 * should complete the task in. Users can also mark the task as done or undone.
 */
public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructor method for Event class.
     * @param desc The description of the task.
     * @param startDate The date in which the task should begin.
     * @param endDate The date in which the task should be completed by.
     * @throws EgoException If the format of the Event task as inputted by the user in
     * their command is invalid.
     */
    public Event(String desc, String startDate, String endDate) throws EgoException {
        super(desc);
        try {
            this.startDate = LocalDate.parse(startDate);
            this.endDate = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new EgoException("Hey there! Please enter the date in yyyy-MM-dd format!, eg. 2025-08-25");
        }
    }

    /**
     * Constructor method for Event class.
     * @param description The description of the task.
     * @param startDate The date in which the task should begin.
     * @param endDate The date in which the the task should be completed by.
     */
    public Event(String description, LocalDate startDate, LocalDate endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns a String representation of the Event object in the correct format to be stored in
     * storage.
     * @return A correct String representation of the Event object to be stored in storage.
     */
    @Override
    public String toFileFormat() {
        return "E | "+ (isDone ? "1" : "0") + " | " + description +
                " | " + startDate + " | " + endDate;
    }

    /**
     * Returns a String representation of the Event object as displayed to user.
     * @return A correct String representation of the Deadline object as displayed to user.
     */
    @Override
    public String toString() {
        String duration = "(from: " +
                this.startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " to: "
                + this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))+ ")";
        return "[E]" + super.toString() + duration;
    }
}
