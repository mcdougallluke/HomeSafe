import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class KeyPadApp extends Application {


    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int number = i * 3 + j + 1;
                Button btn = createButton(String.valueOf(number));
                gridPane.add(btn, j, i);
            }
        }

        Button zeroButton = createButton("0");
        gridPane.add(zeroButton, 1, 3);

        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setTitle("KeyPad App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(60, 60);
        btn.setOnAction(event -> System.out.println(text));
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
