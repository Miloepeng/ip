public class Task {
    private String name;
    private boolean done;

    public Task(String name) {
        this.done = false;
        this.name = name;
    }

    public void doTask() {
        this.done = true;
    }

    public void undoTask() {
        this.done = false;
    }

    @Override
    public String toString() {
        String status = "";
        if (this.done) {
            status = "[X]";
        } else {
            status = "[ ]";
        }
        return status + " " + this.name;
    }
}
