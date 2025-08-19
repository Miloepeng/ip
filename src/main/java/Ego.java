import java.util.Scanner;
import java.util.ArrayList;

public class Ego {
    private static ArrayList<String> items = new ArrayList<>();

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
     * Listens to user's input continuously to add or display list
     */
    public static void echo() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                list();
                input = scanner.nextLine();
            } else {
                add(input);
                input = scanner.nextLine();
            }
        }
        bye();
    }

    /**
     * Adds a given item to the list and prints a confirmation message
     * @param item The item to be added to the list
     */
    public static void add(String item) {
        items.add(item);
        line();
        System.out.println("added: " + item + "\n");
        line();
    }

    /**
     * Prints all current items in the list along with their index numbers
     */
    public static void list() {
        line();
        for (int i = 0; i < items.size(); i++) {
            int count = i+1;
            System.out.println(count + ". " + items.get(i) + "\n");
        }
        line();
    }

    public static void main(String[] args) {
        greet();
        echo();
    }
}
