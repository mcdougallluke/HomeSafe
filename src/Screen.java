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

    public Screen(InputController inputController) {
        this.inputController = inputController;
        this.inputController.setScreen(this);

        line1 = createTextLine();
        line2 = createTextLine();

        textContainer = new VBox(5);
        textContainer.getChildren().addAll(line1, line2);
        textContainer.setAlignment(Pos.CENTER);

        background = new Rectangle(250, 150, Color.web("#252525"));

        screenComponent = new StackPane();
        screenComponent.setAlignment(Pos.CENTER);
        screenComponent.getChildren().addAll(background, textContainer);

        screenComponent.setPrefWidth(250);
        screenComponent.setPrefHeight(150);
        screenComponent.setVisible(false);
    }

    private Text createTextLine() {
        Text text = new Text();
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 35;");
        return text;
    }

    public void displayMessage(String message) {
        clearMessage();

        if (message.length() <= 13) {
            line1.setText(message);
        } else {
            line1.setText(message.substring(0, 13));
            line2.setText(message.substring(13));
        }
    }

    public void displayTempMessage(String message, double seconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> clearMessage());
        pause.play();

        displayMessage(message);
    }

    private void clearMessage() {
        line1.setText("");
        line2.setText("");
    }

    public String getDisplayText() {
        return line1.getText() + line2.getText();
    }

    public void appendKeyEntry(String key) {
        String currentText = getDisplayText() + key;

        if (currentText.length() <= 26) {  // 13 characters * 2 lines
            displayMessage(currentText);
        }
    }

    public void turnOn() {
        screenComponent.setVisible(true);
        displayTempMessage("WELCOME", 2);
    }

    public void turnOff() {
        screenComponent.setVisible(false);
    }

    public StackPane getScreenComponent() {
        return screenComponent;
    }
}
