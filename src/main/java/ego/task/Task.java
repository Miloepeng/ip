package ego.task;

/**
 * Represents a task in the user's task list. It contains information about the task description,
 * deadlines if any, as well as its current status of whether it has been completed.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String name) {
        this.isDone = false;
        this.description = name;
        assert this.description != null : "Task description cannot be null";
    }

    /**
     * Returns the String representation of the task in the correct format to be saved
     * in our storage file.
     * @return A String representation of the task in correct format.
     */
    public abstract String toFileFormat();

    /**
     * Updates the task as being completed.
     */
    public void doTask() {
        this.isDone = true;
    }

    /**
     * Updates the task as being incomplete.
     */
    public void undoTask() {
        this.isDone = false;
    }

    /**
     * Returns a String representation of Task.
     * @return A String representation of Task.
     */
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
