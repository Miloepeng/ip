package ego.task;

/**
 * Represents a Todo task that has a task description. User can mark
 * it as either done or undone.
 */
public class ToDo extends Task {

    /**
     * Constructor method for ToDo class.
     * @param desc The description of the task to be done.
     */
    public ToDo(String desc) {
        super(desc);
    }

    /**
     * Returns a String representation of the ToDo object in the correct format to be stored in
     * storage.
     * @return
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a String representation of the ToDo object as displayed to user.
     * @return A correct String representation of the ToDo object as displayed to user.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
