import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Screen {

    private final TextArea display;
    private final StackPane screenComponent;

    public Screen() {
        display = new TextArea();
        display.setEditable(false);
        display.setWrapText(false);
        display.setScrollTop(Double.MAX_VALUE);
        display.setMaxHeight(40);
        display.setPrefRowCount(1);
        display.setFocusTraversable(false);

        display.setStyle("-fx-background-color: #141314; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20; " +
                "-fx-border-color: #141314; " +
                "-fx-focus-color: #141314; " +
                "-fx-faint-focus-color: #141314; " +
                "-fx-control-inner-background: #141314; " +
                "-fx-padding: 5;");


        // Create a background for the screen
        Rectangle background = new Rectangle(250, 40, Color.web("#141314"));

        screenComponent = new StackPane();
        screenComponent.setAlignment(Pos.CENTER);
        screenComponent.getChildren().addAll(background, display);

        // Define the size of the screen
        screenComponent.setPrefWidth(250);
        screenComponent.setPrefHeight(40);

        final int MAX_CHARACTERS = 19;
        display.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > MAX_CHARACTERS) {
                display.setText(oldValue);
            }
        });
    }

    public void displayMessage(String message) {
        display.setText(message);
    }

    public void displayTempMessage(String message, double seconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> clearMessage());
        pause.play();
        displayMessage(message);
    }

    private void clearMessage() {
        display.clear();
    }

    public String getDisplayText() {
        return display.getText();
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