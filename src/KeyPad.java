// CS 460 Team 01

// Import necessary JavaFX components
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Class representing the keypad component of the UI
public class KeyPad extends GridPane {

    // Sound that plays when a button is pressed
    private final AudioClip buttonSound = new AudioClip(getClass().getResource("audio/KeyPadBeep.mp3").toString());
    // Volume level for the button sound
    private double currentVolume = 0.0;

    // Reference to the InputController to handle button actions
    private final InputController inputController;

    // Constructor initializing the keypad with given input controller
    public KeyPad(InputController inputController) {
        this.inputController = inputController;
        initKeypad();  // Initialize keypad buttons and layout
    }

    // Method to initialize the layout and buttons of the keypad
    private void initKeypad() {
        setLayoutProperties();   // Set layout properties of the keypad
        addNumberButtons();      // Add numeric buttons (1-9)
        addControlButtons();     // Add control buttons like volume, power, etc.
    }

    // Configure general layout settings of the keypad
    private void setLayoutProperties() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setHgap(10);
        this.setVgap(10);
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #a9a9a9, #ffffff, #a9a9a9);");
    }

    // Method to add numeric buttons to the keypad
    private void addNumberButtons() {
        // Loop to create numeric buttons from 1-9
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int number = i * 3 + j + 1;
                Button btn = createButton(String.valueOf(number), String.valueOf(number));
                this.add(btn, j, i);
            }
        }
    }

    // Method to add control buttons to the keypad
    private void addControlButtons() {
        // Create and configure volume up button
        Button volumeUpButton = createButton("^", "volume up");
        volumeUpButton.setOnAction(event -> {
            adjustVolume(0.1);
            buttonSound.play();
        });

        // Create zero button
        Button zeroButton = createButton("0", "0");

        // Create and configure volume down button
        Button volumeDownButton = createButton("v", "volume down");
        volumeDownButton.setOnAction(event -> {
            adjustVolume(-0.1);
            buttonSound.play();
        });

        // Add control buttons to the grid layout
        this.add(volumeUpButton, 0, 3);
        this.add(zeroButton, 1, 3);
        this.add(volumeDownButton, 2, 3);

        // Create and configure cancel button
        Button cancelButton = createButton("X", "cancel");
        cancelButton.setTextFill(Color.RED);
        cancelButton.setOnAction(event -> {
            inputController.handleCancel();
            adjustVolume(0);
            buttonSound.play();
        });

        // Create and configure power button
        Button powerButton = createButton("*", "power");
        powerButton.setOnAction(event -> {
            inputController.handlePowerButton();
            adjustVolume(0);
            buttonSound.play();
        });

        // Create and configure enter button
        Button enterButton = createButton("O", "enter");
        enterButton.setTextFill(Color.GREEN);
        enterButton.setOnAction(event -> {
            inputController.handleEnterButton();
            adjustVolume(0);
            buttonSound.play();
        });

        // Add remaining control buttons to the grid layout
        this.add(cancelButton, 0, 4);
        this.add(powerButton, 1, 4);
        this.add(enterButton, 2, 4);
    }
    
    // Helper method to create and style a button
    private Button createButton(String text, String printText) {
        Button btn = new Button(text);
        btn.setPrefSize(55, 55);
        applyDefaultButtonStyle(btn);

        switch (text) {
            case "*":
                setPowerButtonGraphics(btn);
                break;
            case "X":
                btn.setTextFill(Color.RED);
                break;
            case "O":
                btn.setTextFill(Color.GREEN);
                break;
        }

        btn.setOnAction(event -> {
            inputController.handleKeyInput(printText);
            adjustVolume(0);
            buttonSound.play();
        });

        return btn;
    }

    // Apply the default style to a button
    private void applyDefaultButtonStyle(Button btn) {
        String gradientBackground = "-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #3E3E3E, #2E2E2E);";
        btn.setStyle(gradientBackground + "-fx-text-fill: white; -fx-font-size: 24px;");
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        btn.setEffect(shadow);
        btn.setOnMousePressed(event -> setButtonPressedStyle(btn));
        btn.setOnMouseReleased(event -> applyDefaultButtonStyle(btn));
        if(btn.getText().equals("O")){btn.setStyle(gradientBackground + "-fx-text-fill: green; -fx-font-size: 24px;");}
        if(btn.getText().equals("X")){btn.setStyle(gradientBackground + "-fx-text-fill: red; -fx-font-size: 24px;");}
    }

    // Apply a visual style to the button to indicate it has been pressed
    private void setButtonPressedStyle(Button btn) {
        String pressedStyle = "-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #2E2E2E, #1E1E1E);";
        btn.setStyle(pressedStyle + "-fx-text-fill: white; -fx-font-size: 24px;");
        DropShadow shadow = (DropShadow) btn.getEffect();
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
    }

    // Add a power icon to the power button
    private void setPowerButtonGraphics(Button btn) {
        ImageView imageView = new ImageView(new Image(getClass().getResource("images/PowerButton.png").toString()));
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        btn.setGraphic(imageView);
        btn.setText("");
    }

     // Adjust the button sound volume
    private void adjustVolume(double delta) {
        currentVolume += delta;
        if (currentVolume > 1.0) currentVolume = 1.0;
        else if (currentVolume < 0.0) currentVolume = 0.0;
        buttonSound.setVolume(currentVolume);
    }

}
