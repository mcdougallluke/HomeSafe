import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KeyPadApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: grey;"); // Set GridPane background to grey

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

        Scene scene = new Scene(gridPane, 300, 400);
        primaryStage.setTitle("KeyPad App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text, String printText) {
        Button btn = new Button(text);
        btn.setPrefSize(60, 60);
        btn.setStyle("-fx-background-color: black; -fx-text-fill: white;"); // Set button background to black and text to white
        btn.setOnAction(event -> System.out.println(printText));
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
