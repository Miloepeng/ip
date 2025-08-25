import java.util.Scanner;

public class Ui {
    private Parser parser;

    public Ui(Parser parser) {
        this.parser = parser;
    }

    private void printLine() {
        System.out.println("________________________________________________________");
    }

    public void greet() {
        printLine();
        String greeting = "Hello there diamonds in the rough, I'm Ego *smiles*\n" +
                "How may I be of assistance to your improvement today?";
        System.out.println(greeting);
        printLine();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            String result = this.parser.parseCommand(input);
            printLine();
            System.out.println(result);
            printLine();
            input = scanner.nextLine();
        }
        String result = this.parser.parseCommand(input);
        printLine();
        System.out.println(result);
        printLine();
    }
}
