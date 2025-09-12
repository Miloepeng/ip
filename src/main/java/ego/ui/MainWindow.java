package ego.ui;

import ego.Ego;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Ego ego;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image egoImage = new Image(this.getClass().getResourceAsStream("/images/Ego.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setEgo(Ego ego) {
        this.ego = ego;
        // Show greeting when GUI loads
        dialogContainer.getChildren().add(
                DialogBox.getEgoDialog(ego.getGreeting(), egoImage, null)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = ego.getResponse(input);
        String commandType = ego.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getEgoDialog(response, egoImage, commandType)
        );
        userInput.clear();

        // --- add this block ---
        boolean isExit = "bye".equalsIgnoreCase(input.trim())
                || "BYE".equalsIgnoreCase(commandType); // use your command type if available

        if (isExit) {
            // Optional: stop further input while farewell is visible
            try { userInput.setDisable(true); } catch (Exception ignored) {}
            try { sendButton.setDisable(true); } catch (Exception ignored) {}

            PauseTransition delay = new PauseTransition(Duration.millis(600));
            delay.setOnFinished(e -> {
                // This triggers Main.java's stage.setOnCloseRequest(... -> ego.save())
                Platform.exit();
                // Optional: if you have non-daemon threads, also call:
                // System.exit(0);
            });
            delay.play();
        }
        // --- end add ---
    }
}

