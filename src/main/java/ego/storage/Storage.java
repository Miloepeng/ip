package ego.storage;

import ego.task.Deadline;
import ego.task.Event;
import ego.task.Task;
import ego.task.TaskList;
import ego.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a class that deals with loading tasks from the save file and saving tasks into the save file.
 */
public class Storage {
    private String filePath;
    private static final String DONE_FLAG = "1";
    private static final String DELIMITER = " \\| ";

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the current tasks in the user's task list into the save file.
     * @param taskList The current list of tasks the user is keeping track of.
     */
    public void saveTasks(TaskList taskList) {
        try {
            File file = new File(this.filePath);
            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            for (Task task : taskList.getTasks()) {
                fileWriter.write(task.toFileFormat() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads the tasks from the save file into the user's current session.
     * @return A TaskList which contains the tasks saved by the user in his previous session.
     */
    public TaskList loadTasks() {
        try {
            TaskList result = new TaskList(new ArrayList<>());
            File file = new File(this.filePath);
            if (!file.exists()) {
                return result;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Task task = parseTask(line);
                    result.addTask(task);
                } catch (Exception e) {
                    throw new IllegalStateException("Corrupted task file: " + line, e);
                }
            }
            scanner.close();
            return result;
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return new TaskList(new ArrayList<>());
        }
    }

    /**
     * Parses the task in the save file into the respective Task objects.
     * @param line The task in the save file.
     * @return A Task object representing the task stored in the save file.
     */
    private static Task parseTask(String line) {
        String[] parts = line.split(DELIMITER);
        String type = parts[0];
        boolean isDone = parts[1].equals(DONE_FLAG);
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            task = new Deadline(description, LocalDate.parse(parts[3]));
            break;
        case "E":
            task = new Event(description, LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
            break;
        default:
            throw new IllegalArgumentException("Unknown task type in file: " + type);
        }
        if (isDone) {
            task.doTask();
        }
        return task;
    }
}
