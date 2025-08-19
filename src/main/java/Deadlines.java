public class Deadlines extends Task {
    private String endDate;

    public Deadlines(String name, String endDate) {
        super(name);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.endDate + ")";
    }
}
