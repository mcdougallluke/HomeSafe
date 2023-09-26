import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Objects;


public class SafeGUI extends Application {

    private Screen screen;
    private KeyPad keyPad;
    private Image safeFrontImage;
    private Image safeCloseUpImage;
    private Image safeOpenImage;
    private ImageView imageView;
    private Button closeButton;
    private ButtonPanel buttonPanel;


    private SafeController safeController;

    @Override
    public void start(Stage primaryStage) {
        //StackPane root = new StackPane();
        AnchorPane root = new AnchorPane();

        safeFrontImage = new Image(Objects.requireNonNull(getClass().getResource("images/SAFE_DISPLAY.png")).toExternalForm());
        safeCloseUpImage = new Image(Objects.requireNonNull(getClass().getResource("images/SAFE_FRONT.png")).toExternalForm());
        safeOpenImage = new Image(Objects.requireNonNull(getClass().getResource("images/SAFE_OPEN.png")).toExternalForm());
        imageView = new ImageView(safeFrontImage);
        buttonPanel = new ButtonPanel();

        PINManager pinManager = new PINManager();
        InputController controller = new InputController();
        controller.setPINManager(pinManager);
        screen = new Screen(controller);
        keyPad = new KeyPad(controller);

        safeController = new SafeController(screen, this);
        controller.setSafeController(safeController);

        safeController.setPINManager(pinManager);
        buttonPanel.setSafeController(safeController);
        imageView.setOnMouseClicked(event -> {
            imageView.setImage(safeCloseUpImage);
            AnchorPane anchorPane = new AnchorPane();

            double screenXCoordinate = 80;
            double screenYCoordinate = 110;
            AnchorPane.setTopAnchor(screen.getScreenComponent(), screenYCoordinate);
            AnchorPane.setLeftAnchor(screen.getScreenComponent(), screenXCoordinate);

            double keypadXCoordinate = 175;
            double keypadYCoordinate = 325;
            AnchorPane.setTopAnchor(keyPad, keypadYCoordinate);
            AnchorPane.setLeftAnchor(keyPad, keypadXCoordinate);

            double buttonPanelXCoordinate = imageView.getBoundsInLocal().getWidth(); // This will position the button panel immediately to the right of the imageView
            double buttonPanelYCoordinate = 150;
            AnchorPane.setTopAnchor(buttonPanel.getButtonBox(), buttonPanelYCoordinate);
            AnchorPane.setLeftAnchor(buttonPanel.getButtonBox(), buttonPanelXCoordinate);

            anchorPane.getChildren().addAll(screen.getScreenComponent(), keyPad);

            root.getChildren().addAll(screen.getScreenComponent(), keyPad, buttonPanel.getButtonBox());

            imageView.setOnMouseClicked(null);
        });

        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.setPreserveRatio(true);

        root.getChildren().addAll(imageView);

        Scene scene = new Scene(root, 815, 700);
        primaryStage.setTitle("Digital Safe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void openSafe() {
        imageView.setImage(safeOpenImage);
        screen.getScreenComponent().setVisible(false);
        keyPad.setVisible(false);

        if (closeButton == null) {
            closeButton = new Button("Close Safe");
            closeButton.setOnAction(event -> {
                closeSafe();
                closeButton.setVisible(false);
            });

            AnchorPane.setBottomAnchor(closeButton, 10.0);
            AnchorPane.setLeftAnchor(closeButton, 40.0 );
            AnchorPane.setRightAnchor(closeButton, 40.0);


            ((AnchorPane) imageView.getParent()).getChildren().add(closeButton);
        } else {
            closeButton.setVisible(true);
        }
    }


    public void closeSafe() {
        safeController.setState(SafeState.CLOSED);
        imageView.setImage(safeCloseUpImage);
        screen.getScreenComponent().setVisible(true);
        keyPad.setVisible(true);
        if (closeButton != null) {
            closeButton.setVisible(false);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
