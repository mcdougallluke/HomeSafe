// CS 460 Team 01

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KeyPad extends GridPane {

    private final AudioClip buttonSound = new AudioClip(getClass().getResource("audio/KeyPadBeep.mp3").toString());
    private double currentVolume = 0.0;

    private final InputController inputController;

    public KeyPad(InputController inputController) {
        this.inputController = inputController;
        initKeypad();
    }

    private void initKeypad() {
        setLayoutProperties();
        addNumberButtons();
        addControlButtons();
    }

    private void setLayoutProperties() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setHgap(10);
        this.setVgap(10);
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #a9a9a9, #ffffff, #a9a9a9);");
    }

    private void addNumberButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int number = i * 3 + j + 1;
                Button btn = createButton(String.valueOf(number), String.valueOf(number));
                this.add(btn, j, i);
            }
        }
    }

    private void addControlButtons() {
        Button volumeUpButton = createButton("^", "volume up");
        volumeUpButton.setOnAction(event -> {
            adjustVolume(0.1);
            buttonSound.play();
        });

        Button zeroButton = createButton("0", "0");

        Button volumeDownButton = createButton("v", "volume down");
        volumeDownButton.setOnAction(event -> {
            adjustVolume(-0.1);
            buttonSound.play();
        });

        this.add(volumeUpButton, 0, 3);
        this.add(zeroButton, 1, 3);
        this.add(volumeDownButton, 2, 3);

        Button cancelButton = createButton("X", "cancel");
        cancelButton.setTextFill(Color.RED);
        cancelButton.setOnAction(event -> {
            inputController.handleCancel();
            adjustVolume(0);
            buttonSound.play();
        });

        Button powerButton = createButton("*", "power");
        powerButton.setOnAction(event -> {
            inputController.handlePowerButton();
            adjustVolume(0);
            buttonSound.play();
        });

        Button enterButton = createButton("O", "enter");
        enterButton.setTextFill(Color.GREEN);
        enterButton.setOnAction(event -> {
            inputController.handleEnterButton();
            adjustVolume(0);
            buttonSound.play();
        });

        enterButton.setTextFill(Color.GREEN);

        this.add(cancelButton, 0, 4);
        this.add(powerButton, 1, 4);
        this.add(enterButton, 2, 4);
    }


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

    private void setButtonPressedStyle(Button btn) {
        String pressedStyle = "-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #2E2E2E, #1E1E1E);";
        btn.setStyle(pressedStyle + "-fx-text-fill: white; -fx-font-size: 24px;");
        DropShadow shadow = (DropShadow) btn.getEffect();
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
    }

    private void setPowerButtonGraphics(Button btn) {
        ImageView imageView = new ImageView(new Image(getClass().getResource("images/PowerButton.png").toString()));
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        btn.setGraphic(imageView);
        btn.setText("");
    }

    private void adjustVolume(double delta) {
        currentVolume += delta;
        if (currentVolume > 1.0) currentVolume = 1.0;
        else if (currentVolume < 0.0) currentVolume = 0.0;
        buttonSound.setVolume(currentVolume);
    }

}
