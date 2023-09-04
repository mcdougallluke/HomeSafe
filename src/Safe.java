import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Safe extends Application {

    @Override
    public void start(Stage primaryStage) {
        Image safeImage = new Image("SAFE_FRONT.png");
        ImageView imageView = new ImageView(safeImage);

        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.setPreserveRatio(true);

        KeyPadApp keypadApp = new KeyPadApp();
        GridPane keypad = keypadApp.createKeypad();

        AnchorPane keypadWrapper = new AnchorPane(keypad);

        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, keypadWrapper);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Digital Safe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
