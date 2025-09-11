package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Amadeus;  // Import Amadeus from main package

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

    private Amadeus amadeus;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image amadeusImage = new Image(this.getClass().getResourceAsStream("/images/DaAmadeus.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        
        // Initialize Amadeus and show greeting
        amadeus = new Amadeus();
        String greeting = amadeus.getGreeting();
        dialogContainer.getChildren().add(
            DialogBox.getAmadeusDialog(greeting, amadeusImage)
        );
    }

    /** 
     * Injects the Amadeus instance 
     * @param d The Amadeus instance to set
     */
    public void setAmadeus(Amadeus d) {
        amadeus = d;
        // Clear existing dialogs and show greeting from the new instance
        dialogContainer.getChildren().clear();
        String greeting = amadeus.getGreeting();
        dialogContainer.getChildren().add(
            DialogBox.getAmadeusDialog(greeting, amadeusImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Amadeus's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        
        // Don't process empty input
        if (input.trim().isEmpty()) {
            return;
        }
        
        String response = amadeus.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAmadeusDialog(response, amadeusImage)
        );
        userInput.clear();
        
        // If user says bye, you might want to disable further input or close the window
        if (input.trim().equalsIgnoreCase("bye")) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
    
    /**
     * Handles user input when Enter key is pressed in the text field.
     */
    @FXML
    private void handleEnterPressed() {
        handleUserInput();
    }
}