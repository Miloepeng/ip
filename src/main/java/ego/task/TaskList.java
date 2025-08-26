package ego.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> taskList) {
        this.tasks = taskList;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task getTask(int i) {
        return this.tasks.get(i);
    }

    public Task removeTask(int taskNum) {
        return this.tasks.remove(taskNum);
    }

    @Override
    public String toString() {
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
