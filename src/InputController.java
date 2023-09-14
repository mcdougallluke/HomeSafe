// Handles input from the user via the IRIS scanner and Keypad

public class InputController {

    private Screen screen;

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void handleKeyInput(String key) {
        screen.appendKeyEntry(key);
    }

    public void handleCancel() {
        String currentText = screen.getDisplayText();
        if (!currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1);
            screen.displayMessage(currentText);
        }
    }


    public void handlePowerButton() {
        if(screen.getScreenComponent().isVisible()) {
            screen.turnOff();
        } else {
            screen.turnOn();
        }
    }

}
