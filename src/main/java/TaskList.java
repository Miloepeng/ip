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
}
