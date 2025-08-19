public class Events extends Task{
    private String startDate;
    private String endDate;

    public Events(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String duration = "(from: " + this.startDate + " to: "
                + this.endDate + ")";
        return "[E]" + super.toString() + " " + duration;
    }
}
