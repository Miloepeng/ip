package ego.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String name) {
        this.isDone = false;
        this.description = name;
    }

    public abstract String toFileFormat();

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
        return status + " " + this.description;
    }
}
