import javafx.application.Application;
import javafx.scene.Scene;
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

        // Create the screen and get its component
        // Create the screen and get its component
        Screen screen = new Screen();
        StackPane screenComponent = screen.getScreenComponent();

        KeyPad keypadApp = new KeyPad(screen);
        GridPane keypad = keypadApp.createKeypad();

        AnchorPane keypadWrapper = new AnchorPane(keypad, screenComponent); // Add the screen component to the wrapper

        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, keypadWrapper);

        // Position the keypad
        double keypadXCoordinate = 175;
        double keypadYCoordinate = 325;
        AnchorPane.setTopAnchor(keypad, keypadYCoordinate);
        AnchorPane.setLeftAnchor(keypad, keypadXCoordinate);

        // Position the screen above the keypad
        double screenXCoordinate = 70;
        double screenYCoordinate = 95;
        AnchorPane.setTopAnchor(screenComponent, screenYCoordinate);
        AnchorPane.setLeftAnchor(screenComponent, screenXCoordinate);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Digital Safe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
