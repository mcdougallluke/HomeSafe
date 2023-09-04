import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Screen {

    private final TextArea display;
    private final StackPane screenComponent;

    public Screen() {
        display = new TextArea();
        display.setEditable(false);
        display.setWrapText(true);
        display.setStyle("-fx-background-color: black; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20; " +
                "-fx-border-color: black; " +
                "-fx-focus-color: black; " +
                "-fx-faint-focus-color: black; " +
                "-fx-control-inner-background: black; " +
                "-fx-padding: 0;");

        // Create a background for the screen
        Rectangle background = new Rectangle(250, 50, Color.BLACK); // Set size of the screen to 250x50, adjust as needed

        screenComponent = new StackPane();
        screenComponent.getChildren().addAll(background, display);

        // Define the size of the screen
        screenComponent.setPrefWidth(270);
        screenComponent.setPrefHeight(185);
    }

    // Method to display a message on the screen
    public void displayMessage(String message) {
        display.setText(message);
    }

    // Method to append a key entry to the current display
    public void appendKeyEntry(String key) {
        display.appendText(key);
    }

    // Method to clear the screen
    public void clear() {
        display.clear();
    }

    // Method to get the JavaFX component representing the screen
    public StackPane getScreenComponent() {
        return screenComponent;
    }
}
