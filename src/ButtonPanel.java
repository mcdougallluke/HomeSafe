import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonPanel {

    private VBox buttonBox;

    public ButtonPanel() {
        // Create a VBox to stack the buttons vertically
        buttonBox = new VBox(30); // 10 is the spacing between the buttons
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Define an array of names
        String[] names = {"Luke", "Anrei", "Marina", "Jack", "Spork", "Vamsi"};

        // Ensure we don't exceed the length of the names array
        int numButtons = Math.min(names.length, 6);

        // Create buttons using names from the array
        for (int i = 0; i < numButtons; i++) {
            Button btn = new Button(names[i] + " eye");
            btn.setMinHeight(40); // Set a minimum height for the button
            btn.setMinWidth(75);

            // Attach an event to show the GIF on a new window
            btn.setOnAction(event -> showGIFWindow());

            buttonBox.getChildren().add(btn);
        }
    }


    private void showGIFWindow() {
        // Create a new stage (window)
        Stage gifStage = new Stage();
        gifStage.setTitle("GIF Display");

        // Load the GIF
        Image gifImage = new Image(getClass().getResourceAsStream("images/eye.gif"));
        ImageView gifView = new ImageView(gifImage);

        // Wrap the ImageView in a StackPane
        StackPane layout = new StackPane();
        layout.getChildren().add(gifView);

        // Set the layout to the scene and show the window
        gifStage.setScene(new Scene(layout));
        gifStage.sizeToScene(); // Adjust window size to fit GIF
        gifStage.show();
    }

    // Method to retrieve the VBox containing the buttons
    public VBox getButtonBox() {
        return buttonBox;
    }
}
