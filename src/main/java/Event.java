/**
 * Represents an Event task with a task description alongside the time period the user
 * should complete the task in. Users can also mark the task as done or undone.
 */
public class Event extends Task{
    private String startDate;
    private String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String duration = "(from: " + this.startDate + " to: "
                + this.endDate + ")";
        return "[E]" + super.toString() + duration;
    }
}
