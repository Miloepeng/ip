import java.util.Scanner;

public class Ego {
    public static void main(String[] args) {
        greet();
        echo();
    }

    public static void line() {
        System.out.println("________________________________________________________\n");
    }

    public static void greet() {
        line();
        String greeting = "Hello there diamonds in the rough, I'm Ego *smiles*\n" +
                "How may I be of assistance to your improvement today?\n";
        System.out.println(greeting);
        line();
    }

    public static void bye() {
        line();
        String bye = "Farewell... see you soon";
        System.out.println(bye);
        line();
    }

    public static void echo() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            line();
            System.out.println(input + "\n");
            line();
            input = scanner.nextLine();
        }
        bye();
    }
}
