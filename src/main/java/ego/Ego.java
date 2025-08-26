package ego;

import ego.exception.EgoException;
import ego.parser.Parser;
import ego.storage.Storage;
import ego.task.TaskList;
import ego.ui.Ui;

import java.util.Scanner;

public class Ego {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Ego(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = this.storage.loadTasks();
        this.parser = new Parser(this.tasks, this.storage);
        this.ui = new Ui();
    }

    /**
     * Listens to user's input continuously to add or display ego.task.Task list. User can also opt to mark Tasks
     * as done or undone.
     */
    public void run() {
        this.ui.greet();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            try {
                String result = this.parser.parseCommand(input);
                this.ui.printMessage(result);
            } catch (EgoException e) {
                this.ui.printMessage(e.getMessage());
            }
            input = scanner.nextLine();
        }
        try {
            String result = this.parser.parseCommand(input);
            this.ui.printMessage(result);
        } catch (EgoException e) {
            this.ui.printMessage("Error: " + e.getMessage());
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new Ego("data/ego.txt").run();
    }
}
