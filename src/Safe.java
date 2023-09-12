import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Safe extends Application {

    private Screen screen;
    private KeyPad keyPad;
    private Image safeFrontImage;
    private Image safeCloseUpImage;


    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        safeFrontImage = new Image(Objects.requireNonNull(getClass().getResource("images/SAFE_DISPLAY.png")).toExternalForm());
        safeCloseUpImage = new Image(Objects.requireNonNull(getClass().getResource("images/SAFE_FRONT.png")).toExternalForm());
        ImageView imageView = new ImageView(safeFrontImage);
        screen = new Screen();
        keyPad = new KeyPad(screen);

        imageView.setOnMouseClicked(event -> {
            imageView.setImage(safeCloseUpImage);

            // Create an AnchorPane and add keypad and screen to it
            AnchorPane anchorPane = new AnchorPane();

            // Position the screen above the keypad
            double screenXCoordinate = 75;
            double screenYCoordinate = 110;
            AnchorPane.setTopAnchor(screen.getScreenComponent(), screenYCoordinate);
            AnchorPane.setLeftAnchor(screen.getScreenComponent(), screenXCoordinate);

            // Position the keypad
            double keypadXCoordinate = 175;
            double keypadYCoordinate = 325;
            AnchorPane.setTopAnchor(keyPad.getKeyPadComponent(), keypadYCoordinate);
            AnchorPane.setLeftAnchor(keyPad.getKeyPadComponent(), keypadXCoordinate);

            anchorPane.getChildren().addAll(screen.getScreenComponent(), keyPad.createKeypad());

            root.getChildren().add(anchorPane); // Add AnchorPane to root StackPane

            imageView.setOnMouseClicked(null);  // Unregister the click handler after the first click
        });

        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.setPreserveRatio(true);

        root.getChildren().addAll(imageView);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Digital Safe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
