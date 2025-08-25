import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage (String filePath) {
        this.filePath = filePath;
    }

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
            System.out.println("Error loading tasks:" + e.getMessage());
            return new TaskList(new ArrayList<>());
        }
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
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
