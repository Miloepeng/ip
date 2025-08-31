package ego.task;

import java.util.ArrayList;

/**
 * Represents the user's task list containing either Todos, Deadlines, or Events.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> taskList) {
        this.tasks = taskList;
    }

    /**
     * Returns the user's task list.
     * @return A ArrayList<Task> of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Adds a given task to the current task list.
     * @param task The task to be added to the task list.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the current number of tasks present in the task list.
     * @return The number of tasks in the task list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Returns the Task object at the index requested by the user.
     * @param index The index of the task requested by the user.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Removes the task at the index as given by the user.
     * @param taskNum The index of the task to be removed.
     * @return The Task object that should be removed from the task list.
     */
    public Task removeTask(int taskNum) {
        return this.tasks.remove(taskNum);
    }

    /**
     * Returns the String representation of TaskList.
     * @return A String representation of TaskList.
     */
    @Override
    public String toString() {
        if (this.tasks.size() == 0) {
            return "Uhoh! Looks like your tasklist is empty...";
        }
        String msg = "";
        for (int i = 0; i < this.tasks.size(); i++) {
            int count = i + 1;
            if (count < this.tasks.size()) {
                msg += count + "." + this.tasks.get(i) + "\n";
            } else {
                msg += count + "." + this.tasks.get(i);
            }
        }
        return msg;
    }
}
