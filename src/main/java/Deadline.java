/**
 * Represents a Deadline task with a task description and a due date in which
 * the task should be completed by. Users can mark the task as done or undone.
 */
public class Deadline extends Task {
    private String endDate;

    public Deadline(String name, String endDate) {
        super(name);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.endDate + ")";
    }
}
