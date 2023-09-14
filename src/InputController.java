// Handles input from the user via the IRIS scanner and Keypad

public class InputController {

    private Screen screen;
    private SafeController safeController;

    public void setSafeController(SafeController safeController) {
        this.safeController = safeController;
    }

    // Now, use the safeController to change states based on specific inputs.
    // Example:
    public void onUnlockButtonPressed() {
        safeController.setState(SafeState.UNLOCKED);
    }
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
            // If you want to handle other logic when turning off, place it here.
        } else {
            screen.turnOn();
            // Check the current state of the safe and transition to set up if needed.
            if (safeController.getCurrentState() == SafeState.OFF) {
                safeController.setState(SafeState.INITIAL_PIN_SETUP);
            }
        }
    }


}
