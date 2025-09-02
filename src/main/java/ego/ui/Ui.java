package ego.ui;

/**
 * Represents a class that deals with interactions with the user, known as the user interface.
 */
public class Ui {

    public Ui() {
    }

    /**
     * Displays a line to segment the response given by the chatbot for easy visibility.
     */
    private void printLine() {
        System.out.println("________________________________________________________");
    }

    /**
     * Displays a greeting to the user when the user first loads up the chatbot.
     */
    public void greet() {
        printLine();
        String greeting = getGreeting();
        System.out.println(greeting);
        printLine();
    }

    /**
     * Returns the greeting text for display by the GUI.
     */
    public String getGreeting() {
        return "Hello there diamonds in the rough, I'm ego.Ego *smiles*\n"
                + "How may I be of assistance to your improvement today?";
    }

    /**
     * Displays the intended response to the user after processing the user's command.
     * @param message The response to the command as processed by Parser.
     */
    public void printMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }
}
