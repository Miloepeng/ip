import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Ego {
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static final String FILE_PATH = "./data/ego.txt";

    /**
     * Prints separator line.
     */
    public static void printLine() {
        System.out.println("________________________________________________________");
    }

    /**
     * Prints greeting message to welcome user.
     */
    public static void greet() {
        printLine();
        String greeting = "Hello there diamonds in the rough, I'm Ego *smiles*\n" +
                "How may I be of assistance to your improvement today?";
        System.out.println(greeting);
        printLine();
    }

    /**
     * Prints goodbye message when user is done.
     */
    public static void sayBye() {
        printLine();
        String bye = "Farewell... see you soon";
        System.out.println(bye);
        printLine();
        saveTasks();
    }

    /**
     * Listens to user's input continuously to add or display Task list. User can also opt to mark Tasks
     * as done or undone.
     */
    public static void echo() {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            try {
                CommandType command = CommandType.fromString(input);

                switch (command) {
                case LIST:
                    listTasks();
                    break;

                case MARK:
                    int markNum = Integer.parseInt(input.substring(5));
                    markTask(markNum);
                    break;

                case UNMARK:
                    int unmarkNum = Integer.parseInt(input.substring(7));
                    unmarkTask(unmarkNum);
                    break;

                case TODO:
                case DEADLINE:
                case EVENT:
                    addTask(input);
                    break;

                case DELETE:
                    int delNum = Integer.parseInt(input.substring(7));
                    deleteTask(delNum);
                    break;

                case INVALID:
                    throw new EgoException("Sorry! " + input + " is a invalid command. Try something else?");

                }

                input = scanner.nextLine();

            } catch (EgoException e) {
                System.out.println(e.getMessage());
                input = scanner.nextLine();
            }
        }
        sayBye();
    }

    /**
     * Adds a given task to the list and prints a confirmation message.
     * @param input The task to be added to the list
     */
    public static void addTask(String input) throws EgoException {
        Task newTask = null;
        TaskType type = TaskType.fromString(input);

        switch (type) {
        case TODO:
            String todo = input.substring(4).trim();
            if (todo.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist.");
            }
            newTask = new ToDo(todo);
            break;

        case DEADLINE:
            String deadline = input.substring(8).trim();
            if (deadline.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (!deadline.contains("/by")) {
                throw new EgoException("Hey... Your command should be in the format: deadline <description>" +
                        " /by <end date>!");
            }
            String[] splitted = deadline.split("/by ");
            if (splitted.length < 1 || splitted[0].isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (splitted.length < 2 || splitted[1].trim().isEmpty()) {
                throw new EgoException("Hey... remind me what's the deadline again? " +
                        "Your command should be in the format deadline <description>" +
                        " /by <end date>!");
            }
            String dueDate = splitted[1].trim();
            newTask = new Deadline(splitted[0], dueDate);
            break;

        case EVENT:
            String event = input.substring(5).trim();
            if (event.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (!event.contains("/from")) {
                throw new EgoException("Hey... where is your /from! " +
                        "Your command should be in the format: deadline <description>" +
                        " /from <start date> /to <end date>!");
            }
            if (!event.contains("/to")) {
                throw new EgoException("Hey... where is your /to! " +
                        "Your command should be in the format: deadline <description>" +
                        " /from <start date> /to <end date>!");
            }
            String[] splitEvent = event.split("/from ");
            event = splitEvent[0];
            if (splitEvent[0].isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            splitEvent = splitEvent[1].split("/to ");
            if (splitEvent[0].isEmpty()) {
                throw new EgoException("Hey... start date must be provided!" +
                        " Your command should be in the format: deadline <description> " +
                        "/from <start date> /to <end date>.");
            }
            if (splitEvent.length < 2 || splitEvent[1].isEmpty()) {
                throw new EgoException("Hey... end date must be provided!" +
                        " Your command should be in the format: deadline <description> " +
                        "/from <start date> /to <end date>.");
            }
            String startDate = splitEvent[0].trim();
            String endDate = splitEvent[1].trim();
            newTask = new Event(event, startDate, endDate);
            break;

        }

        taskList.add(newTask);
        printLine();
        System.out.println("Added: " + newTask );
        System.out.println("Now you have " + taskList.size() + " tasks to complete!");
        printLine();
    }

    /**
     * Prints all current tasks in the list along with their index numbers and completion status.
     */
    public static void listTasks() {
        printLine();
        String msg = "OK egoist, ready to rock your to-do list?";
        System.out.println(msg);
        for (int i = 0; i < taskList.size(); i++) {
            int count = i + 1;
            System.out.println(count + "." + taskList.get(i));
        }
        printLine();
    }

    /**
     * Marks a task as completed based on the task number.
     * @param taskNum the number to identify the task based on the list
     */
    public static void markTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > taskList.size()) {
            throw new EgoException("Wow! Please input a number from 1 to " + taskList.size());
        }
        String msg = "Well done egoist, I've marked this task as completed:\n  ";
        taskList.get(taskNum - 1).doTask();
        printLine();
        System.out.println(msg + taskList.get(taskNum - 1));
        printLine();
    }

    /**
     * Unmarks a task as not completed based on task number.
     * @param taskNum the number to identify the task based on the list
     */
    public static void unmarkTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > taskList.size()) {
            throw new EgoException("Wow! Please input a number from 1 to " + taskList.size());
        }
        String msg = "Alright... I'll mark this task as not done yet...\n  ";
        taskList.get(taskNum - 1).undoTask();
        printLine();
        System.out.println(msg + taskList.get(taskNum - 1));
        printLine();
    }

    public static void deleteTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > taskList.size()) {
            throw new EgoException("Wow! Please input a number from 1 to " + taskList.size());
        }
        String msg = "Roger, I'll delete this task from your list!\n  ";
        Task deletedTask = taskList.remove(taskNum - 1);
        printLine();
        System.out.println(msg + deletedTask);
        System.out.println("Now you have " + taskList.size() + " tasks to complete!");
        printLine();
    }

    private static void saveTasks() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            for (Task task : taskList) {
                fileWriter.write(task.toFileFormat() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Task task = parseTask(line);
                    taskList.add(task);
                } catch (Exception e) {
                    throw new IllegalStateException("Corrupted task file: " + line, e);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks:" + e.getMessage());
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

    public static void main(String[] args) {
        greet();
        echo();
    }
}
