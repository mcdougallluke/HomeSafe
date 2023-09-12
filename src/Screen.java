import javafx.geometry.Pos;
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
        // Prevents line wrapping
        display.setWrapText(false);
        // Always scroll to the bottom (though since we're preventing scrolling, this is more of a safety measure)
        display.setScrollTop(Double.MAX_VALUE);
        display.setMaxHeight(40); // Set the max height slightly more than font size to ensure proper padding
        display.setPrefRowCount(1); // Only one line

        display.setStyle("-fx-background-color: #141314; " +  // Changed the background color to #141314
                "-fx-text-fill: white; " +
                "-fx-font-size: 35; " +
                "-fx-border-color: #141314; " +  // Changed the border color to #141314
                "-fx-focus-color: #141314; " +  // Changed the focus color to #141314
                "-fx-faint-focus-color: #141314; " +  // Changed the faint focus color to #141314
                "-fx-control-inner-background: #141314; " +  // Changed the control inner background color to #141314
                "-fx-padding: 5;");

        // Create a background for the screen
        Rectangle background = new Rectangle(250, 40, Color.web("#141314")); // Adjusted the color to #141314

        screenComponent = new StackPane();
        screenComponent.setAlignment(Pos.CENTER);
        screenComponent.getChildren().addAll(background, display);

        // Define the size of the screen
        screenComponent.setPrefWidth(250);
        screenComponent.setPrefHeight(40);

        final int MAX_CHARACTERS = 10;
        display.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > MAX_CHARACTERS) {
                display.setText(oldValue);
            }
        });
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
