package ego.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<Task> getTasks() {
        return this.taskList;
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public int getSize() {
        return this.taskList.size();
    }

    public Task getTask(int i) {
        return this.taskList.get(i);
    }

    public Task removeTask(int taskNum) {
        return this.taskList.remove(taskNum);
    }

    @Override
    public String toString() {
        if (this.taskList.size() == 0) {
            return "Uhoh! Looks like your tasklist is empty...";
        }
        String msg = "";
        for (int i = 0; i < this.taskList.size(); i++) {
            int count = i + 1;
            if (count < this.taskList.size()) {
                msg += count + "." + this.taskList.get(i) + "\n";
            } else {
                msg += count + "." + this.taskList.get(i);
            }
        }
        return msg;
    }
}
