import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonPanel {

    private VBox buttonBox;

    public ButtonPanel() {
        // Create a VBox to stack the buttons vertically
        buttonBox = new VBox(10); // 10 is the spacing between the buttons
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Create 6 buttons and add them to the VBox
        for (int i = 1; i <= 6; i++) {
            Button btn = new Button("Button " + i);
            btn.setMinHeight(40); // Set a minimum height for the button
            buttonBox.getChildren().add(btn);
        }
    }

    // Method to retrieve the VBox containing the buttons
    public VBox getButtonBox() {
        return buttonBox;
    }
}
