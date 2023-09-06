import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;


public class KeyPadApp {

    private Screen screen;

    public KeyPadApp(Screen screen) {
        this.screen = screen;
    }

    public GridPane createKeypad() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10)); // Add padding to create a border around the buttons
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: linear-gradient(to bottom, #a9a9a9, #ffffff, #a9a9a9);");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int number = i * 3 + j + 1;
                Button btn = createButton(String.valueOf(number), String.valueOf(number));
                gridPane.add(btn, j, i);
            }
        }

        Button volumeUpButton = createButton("^", "volume up");
        Button zeroButton = createButton("0", "0");
        Button volumeDownButton = createButton("v", "volume down");

        gridPane.add(volumeUpButton, 0, 3);
        gridPane.add(zeroButton, 1, 3);
        gridPane.add(volumeDownButton, 2, 3);


        Button cancelButton = createButton("X", "cancel");
        cancelButton.setTextFill(Color.RED);  // Set the X button text color to red
        Button asteriskButton = createButton("*", "*");
        Button enterButton = createButton("O", "enter");
        enterButton.setTextFill(Color.GREEN); // Set the O button text color to green

        gridPane.add(cancelButton, 0, 4);
        gridPane.add(asteriskButton, 1, 4);
        gridPane.add(enterButton, 2, 4);

        return gridPane;
    }

    private Button createButton(String text, String printText) {
        Button btn = new Button(text);
        btn.setPrefSize(55, 55);

        // Linear gradient to give depth to the button
        String gradientBackground = "-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #3E3E3E, #2E2E2E);";

        // Set the button's gradient and text color
        if (text.equals("X")) {
            btn.setStyle(gradientBackground + "-fx-text-fill: red; -fx-font-size: 24px;");
        } else if (text.equals("O")) {
            btn.setStyle(gradientBackground + "-fx-text-fill: green; -fx-font-size: 24px;");
        } else {
            btn.setStyle(gradientBackground + "-fx-text-fill: white; -fx-font-size: 24px;");
        }
        // Drop shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        btn.setEffect(shadow);

        // Style when button is pressed: Adjust gradient and reduce shadow to simulate button press
        btn.setOnMousePressed(event -> {
            if (text.equals("X")) {
                btn.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #2E2E2E, #1E1E1E);" + "-fx-text-fill: red; -fx-font-size: 24px;");
            } else if (text.equals("O")) {
                btn.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #2E2E2E, #1E1E1E);" + "-fx-text-fill: green; -fx-font-size: 24px;");
            } else {
                btn.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #2E2E2E, #1E1E1E);" + "-fx-text-fill: white; -fx-font-size: 24px;");
            }
            shadow.setOffsetX(1);
            shadow.setOffsetY(1);
        });


        // Reset to default style when button is released
        btn.setOnMouseReleased(event -> {
            if (text.equals("X")) {
                btn.setStyle(gradientBackground + "-fx-text-fill: red; -fx-font-size: 24px;");
            } else if (text.equals("O")) {
                btn.setStyle(gradientBackground + "-fx-text-fill: green; -fx-font-size: 24px;");
            } else {
                btn.setStyle(gradientBackground + "-fx-text-fill: white; -fx-font-size: 24px;");
            }
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
        });


        btn.setOnAction(event -> screen.appendKeyEntry(printText));

        return btn;
    }

}