package ego.ui;

public class Ui {

    public Ui() {
    }

    private void printLine() {
        System.out.println("________________________________________________________");
    }

    public void greet() {
        printLine();
        String greeting = "Hello there diamonds in the rough, I'm ego.Ego *smiles*\n" +
                "How may I be of assistance to your improvement today?";
        System.out.println(greeting);
        printLine();
    }

    public void printMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }
}
