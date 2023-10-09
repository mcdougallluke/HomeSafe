import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EyeButtons {

    private VBox buttonBox;                // Container for buttons
    private SafeController safeController; // Reference to the SafeController

    // Setter method to set the SafeController
    public void setSafeController(SafeController safeController) {
        this.safeController = safeController;
    }

    // Constructor to initialize the EyeButtons
    public EyeButtons() {
        // Initialize VBox with spacing between its child nodes
        buttonBox = new VBox(30);
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // Align contents to the right

        // Predefined names for the buttons
        String[] names = {"Marina", "Andrei", "Luke", "Spork", "Vamsi", "Jack"};

        // Create buttons for each name in the list (up to a max of 6)
        for (int i = 0; i < Math.min(names.length, 6); i++) {
            Button btn = new Button(names[i] + " eye");
            btn.setMinHeight(40);
            btn.setMinWidth(75);

            // Set an action for each button to show a GIF and interact with the SafeController
            int finalI = i;
            btn.setOnAction(event -> {
                showGIFWindow(); // Display the GIF
                if(safeController != null) {
                    // Handle different states and interactions with SafeController
                    // ... (omitted for brevity in comment) ...
                }
            });

            // Add the button to the VBox container
            buttonBox.getChildren().add(btn);
        }
    }

    // Method to toggle visibility of the button box
    public void setButtonBoxVisible(boolean visible) {
        buttonBox.setVisible(visible);
    }

    // Method to show a window with a GIF
    private void showGIFWindow() {
        // Create a new stage for the GIF
        Stage gifStage = new Stage();
        gifStage.setTitle("GIF Display");

        // Load the GIF and display it using ImageView
        Image gifImage = new Image(getClass().getResourceAsStream("images/eye.gif"));
        ImageView gifView = new ImageView(gifImage);

        // Use a StackPane as the root layout node
        StackPane layout = new StackPane();
        layout.getChildren().add(gifView);

        // Set the scene and display the stage
        gifStage.setScene(new Scene(layout));
        gifStage.sizeToScene();
        gifStage.show();

        // Close the GIF window after a 2-second delay
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> gifStage.close());
        delay.play();
    }

    // Getter method to access the button box (VBox)
    public VBox getButtonBox() {
        return buttonBox;
    }
}
