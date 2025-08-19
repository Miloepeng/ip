import java.util.Scanner;
import java.util.ArrayList;

public class Ego {
    private static ArrayList<Task> items = new ArrayList<>();

    /**
     * Prints separator line
     */
    public static void line() {
        System.out.println("________________________________________________________\n");
    }

    /**
     * Prints greeting message to welcome user
     */
    public static void greet() {
        line();
        String greeting = "Hello there diamonds in the rough, I'm Ego *smiles*\n" +
                "How may I be of assistance to your improvement today?\n";
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
            if (input.equals("list")) {
                listTasks();
                input = scanner.nextLine();
            } else if (input.startsWith("mark")){
                int taskNum = Integer.parseInt(input.substring(5));
                markTask(taskNum);
                input = scanner.nextLine();
            } else if (input.startsWith("unmark")) {
                int taskNum = Integer.parseInt(input.substring(7));
                unmarkTask(taskNum);
                input = scanner.nextLine();
            } else {
                addTask(new Task(input));
                input = scanner.nextLine();
            }
        }
        bye();
    }

    /**
     * Adds a given task to the list and prints a confirmation message
     * @param item The item to be added to the list
     */
    public static void addTask(Task item) {
        items.add(item);
        line();
        System.out.println("added: " + item + "\n");
        line();
    }

    /**
     * Prints all current tasks in the list along with their index numbers
     */
    public static void listTasks() {
        line();
        String msg = "OK egoist, ready to rock your to-do list?";
        System.out.println(msg);
        for (int i = 0; i < items.size(); i++) {
            int count = i+1;
            System.out.println(count + "." + items.get(i) + "\n");
        }
        line();
    }

    /**
     * Marks a task as completed based on the task number
     * @param Tasknum the number to identify the task based on the list
     */
    public static void markTask(int Tasknum) {
        String msg = "Well done egoist, I've marked this task as completed: ";
        items.get(Tasknum - 1).doTask();
        line();
        System.out.println(msg + "\n   " + items.get(Tasknum - 1));
        line();
    }

    /**
     * Unmark a task as not completed based on task number
     * @param Tasknum the number to identify the task based on the list
     */
    public static void unmarkTask(int Tasknum) {
        String msg = "Alright... I'll mark this task as not done yet...";
        items.get(Tasknum - 1).undoTask();
        line();
        System.out.println(msg + "\n   " + items.get(Tasknum - 1));
        line();
    }

    public static void main(String[] args) {
        greet();
        echo();
    }
}
