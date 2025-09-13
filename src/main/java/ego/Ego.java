package ego;

import ego.exception.EgoException;
import ego.parser.Parser;
import ego.storage.Storage;
import ego.task.TaskList;
import ego.ui.Ui;
import ego.ui.Main;

import javafx.application.Application;
import java.util.Scanner;

/**
 * Represents the chatbot Ego.
 */
public class Ego {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private String commandType;

    public Ego(String filePath) {
        this.storage = new Storage(filePath);
        assert this.storage != null : "Storage cannot be null";
        this.tasks = this.storage.loadTasks();
        assert this.tasks != null : "Loaded task lists cannot be null";
        this.parser = new Parser(this.tasks, this.storage);
        assert this.parser != null : "Parser cannot be null";
        this.ui = new Ui();
        assert this.ui != null : "Ui cannot be null";
    }

    /**
     * Listens to user's input continuously to add or display ego.task.Task list. User can also opt to mark Tasks
     * as done or undone, alongside other functionalities such as deleting tasks from their task list or finding
     * specific tasks using keywords.
     */

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            String result = this.parser.parseCommand(input);
            // Map our command enum to dialog style keys
            switch (ego.command.CommandType.fromString(input)) {
            case TODO:
            case DEADLINE:
            case EVENT:
                this.commandType = "AddCommand";
                break;
            case MARK:
            case UNMARK:
                this.commandType = "ChangeMarkCommand";
                break;
            case DELETE:
                this.commandType = "DeleteCommand";
                break;
            default:
                this.commandType = null;
            }
            return result;
        } catch (EgoException e) {
            this.commandType = null;
            return e.getMessage();
        }
    }

    public String getCommandType() {
        return this.commandType;
    }

    /**
     * Returns the greeting message to display on GUI startup.
     */
    public String getGreeting() {
        return this.ui.getGreeting();
    }

    /**
     * Persists tasks to storage. Call on GUI close.
     */
    public void save() {
        this.storage.saveTasks(this.tasks);
    }
}
