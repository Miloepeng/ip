public class Event extends Task{
    private String startDate;
    private String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
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
        String duration = "(from: " + this.startDate + " to: "
                + this.endDate + ")";
        return "[E]" + super.toString() + duration;
    }
}
