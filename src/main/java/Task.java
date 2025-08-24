public class Task {
    private String name;
    private boolean isDone;

    public Task(String name) {
        this.isDone = false;
        this.name = name;
    }

    public void doTask() {
        this.isDone = true;
    }

    public void undoTask() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String status = "";
        if (this.isDone) {
            status = "[X]";
        } else {
            status = "[ ]";
        }
        return status + " " + this.name;
    }
}
