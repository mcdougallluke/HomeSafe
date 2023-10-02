// CS 460 Team 01

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Screen {

    private final Text line1;
    private final Text line2;
    private final VBox textContainer;
    private final Rectangle background;
    private final InputController inputController;
    private final StackPane screenComponent;
    private final StringBuilder actualInput;
    private final PauseTransition timeoutTransition;

    public Screen(InputController inputController) {
        this.inputController = inputController;
        this.inputController.setScreen(this);
        this.actualInput = new StringBuilder();
        timeoutTransition = new PauseTransition(Duration.seconds(3));
        timeoutTransition.setOnFinished(event -> timeout());

        line1 = createTextLine();
        line2 = createTextLine();

        textContainer = new VBox(5);
        textContainer.getChildren().addAll(line1, line2);
        textContainer.setAlignment(Pos.CENTER);

        background = new Rectangle(255, 160, Color.web("#252525"));

        screenComponent = new StackPane();
        screenComponent.setAlignment(Pos.CENTER);
        screenComponent.getChildren().addAll(background, textContainer);

        screenComponent.setPrefWidth(255);
        screenComponent.setPrefHeight(160);
        screenComponent.setVisible(false);
    }

    private Text createTextLine() {
        Text text = new Text();
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 24;");
        return text;
    }

    public void displayMessage(String message) {
        if (message.length() <= 20) {
            line1.setText(message);
        } else {
            line1.setText(message.substring(0, 20));
            line2.setText(message.substring(20));
        }
    }

    public void appendKeyEntry(String key) {
        actualInput.append(key);

        String currentText = line2.getText() + "*";

        if (currentText.length() <= 20) {
            line2.setText(currentText);
        } else {
            line2.setText(currentText.substring(0, 20));
        }

        resetTimeout();
    }

    // reset the timeout timer
    private void resetTimeout() {
        timeoutTransition.stop();
        timeoutTransition.playFromStart();
    }

    public String getCurrentKeyEntry() {
        return actualInput.toString();
    }

    public void clearKeyEntry() {
        actualInput.setLength(0);
        line2.setText("");
    }

    private void timeout() {
        clearKeyEntry();
        displayMessage("Timed out.");
    }

    public void removeLastKeyEntry() {
        String currentText = line2.getText();
        if (!currentText.isEmpty()) {
            actualInput.deleteCharAt(actualInput.length() - 1);
            line2.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void turnOn() {
        screenComponent.setVisible(true);
        displayMessage("WELCOME");
    }

    public void stopTimeout() {
        timeoutTransition.stop();
    }

    public void turnOff() {
        screenComponent.setVisible(false);
    }

    public StackPane getScreenComponent() {
        return screenComponent;
    }
}
