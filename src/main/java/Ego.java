import java.util.Scanner;
import java.util.ArrayList;

public class Ego {
    private static ArrayList<Task> items = new ArrayList<>();

    /**
     * Prints separator line
     */
    public static void line() {
        System.out.println("________________________________________________________");
    }

    /**
     * Prints greeting message to welcome user
     */
    public static void greet() {
        line();
        String greeting = "Hello there diamonds in the rough, I'm Ego *smiles*\n" +
                "How may I be of assistance to your improvement today?";
        System.out.println(greeting);
        line();
    }

    /**
     * Prints goodbye message when user is done
     */
    public static void bye() {
        line();
        String bye = "Farewell... see you soon";
        System.out.println(bye);
        line();
    }

    /**
     * Listens to user's input continuously to add or display Task list. User can also opt to mark Tasks
     * as done or undone
     */
    public static void echo() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    listTasks();
                } else if (input.startsWith("mark")) {
                    int taskNum = Integer.parseInt(input.substring(5));
                    markTask(taskNum);
                } else if (input.startsWith("unmark")) {
                    int taskNum = Integer.parseInt(input.substring(7));
                    unmarkTask(taskNum);
                } else if (input.startsWith("todo") || input.startsWith("deadline") ||
                        input.startsWith("event")) {
                    addTask(input);
                } else if (input.startsWith("delete")) {
                    int taskNum =  Integer.parseInt(input.substring(7));
                    deleteTask(taskNum);
                } else {
                    throw new EgoException("Sorry! " + input + " is a invalid command. Try something else?");
                }
                input = scanner.nextLine();
            } catch (EgoException e) {
                System.out.println(e.getMessage());
                input = scanner.nextLine();
            }
        }
        bye();
    }

    /**
     * Adds a given task to the list and prints a confirmation message
     * @param input The task to be added to the list
     */
    public static void addTask(String input) throws EgoException {
        Task newTask = null;
        if (input.startsWith("todo")) {
            String desc = input.substring(4).trim();
            if (desc.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist.");
            }
            newTask = new ToDos(desc);
        } else if (input.startsWith("deadline")) {
            String desc = input.substring(8).trim();
            if (desc.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (!desc.contains("/by")) {
                throw new EgoException("Hey... Your command should be in the format: deadline <description>" +
                        " /by <end date>!");
            }
            String[] splitted = desc.split("/by ");
            if (splitted.length < 1 || splitted[0].isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (splitted.length < 2 || splitted[1].trim().isEmpty()) {
                throw new EgoException("Hey... remind me what's the deadline again? " +
                        "Your command should be in the format deadline <description>" +
                        " /by <end date>!");
            }
            String endDate = splitted[1];
            newTask = new Deadlines(splitted[0], endDate);
        } else if (input.startsWith("event")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (!desc.contains("/from")) {
                throw new EgoException("Hey... where is your /from! " +
                        "Your command should be in the format: deadline <description>" +
                        " /from <start date> /to <end date>!");
            }
            if (!desc.contains("/to")) {
                throw new EgoException("Hey... where is your /to! " +
                        "Your command should be in the format: deadline <description>" +
                        " /from <start date> /to <end date>!");
            }
            String[] splitted = desc.split("/from ");
            desc = splitted[0];
            if (splitted[0].isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            splitted = splitted[1].split("/to ");
            if (splitted[0].isEmpty()) {
                throw new EgoException("Hey... start date must be provided!" +
                        " Your command should be in the format: deadline <description> " +
                        "/from <start date> /to <end date>.");
            }
            if (splitted.length < 2 || splitted[1].isEmpty()) {
                throw new EgoException("Hey... end date must be provided!" +
                        " Your command should be in the format: deadline <description> " +
                        "/from <start date> /to <end date>.");
            }
            String startDate = splitted[0];
            String endDate = splitted[1];
            newTask = new Events(desc, startDate, endDate);
        }
        items.add(newTask);
        line();
        System.out.println("Added: " + newTask );
        System.out.println("Now you have " + items.size() + " tasks to complete!");
        line();
    }

    /**
     * Prints all current tasks in the list along with their index numbers and completion status
     */
    public static void listTasks() {
        line();
        String msg = "OK egoist, ready to rock your to-do list?";
        System.out.println(msg);
        for (int i = 0; i < items.size(); i++) {
            int count = i+1;
            System.out.println(count + "." + items.get(i));
        }
        line();
    }

    /**
     * Marks a task as completed based on the task number
     * @param taskNum the number to identify the task based on the list
     */
    public static void markTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > items.size()) {
            throw new EgoException("Wow! Please input a number from 1 to " + items.size());
        }
        String msg = "Well done egoist, I've marked this task as completed:\n  ";
        items.get(taskNum - 1).doTask();
        line();
        System.out.println(msg + items.get(taskNum - 1));
        line();
    }

    /**
     * Unmark a task as not completed based on task number
     * @param taskNum the number to identify the task based on the list
     */
    public static void unmarkTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > items.size()) {
            throw new EgoException("Wow! Please input a number from 1 to " + items.size());
        }
        String msg = "Alright... I'll mark this task as not done yet...\n  ";
        items.get(taskNum - 1).undoTask();
        line();
        System.out.println(msg + items.get(taskNum - 1));
        line();
    }

    public static void deleteTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > items.size()) {
            throw new EgoException("Wow! Please input a number from 1 to " + items.size());
        }
        String msg = "Roger, I'll delete this task from your list!\n  ";
        Task deletedTask = items.remove(taskNum-1);
        line();
        System.out.println(msg + deletedTask);
        System.out.println("Now you have " + items.size() + " tasks to complete!");
        line();
    }

    public static void main(String[] args) {
        greet();
        echo();
    }
}
