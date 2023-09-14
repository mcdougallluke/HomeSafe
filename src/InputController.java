// Handles input from the user via the IRIS scanner and Keypad

public class InputController {

    private Screen screen;
    private SafeController safeController;
    private PINManager pinManager; // Added this line

    public void setSafeController(SafeController safeController) {
        this.safeController = safeController;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    // Setter for PinManager
    public void setPINManager(PINManager pinManager) {
        this.pinManager = pinManager;
    }

    public void onUnlockButtonPressed() {
        safeController.setState(SafeState.UNLOCKED);
    }

    public void handleKeyInput(String key) {
        screen.appendKeyEntry(key);
    }

    public void handleCancel() {
        screen.removeLastKeyEntry();
    }

    public void handleEnterButton() {
        safeController.checkPIN(screen.getCurrentKeyEntry());
        screen.clearKeyEntry();
    }

    public void handlePowerButton() {
        if (screen.getScreenComponent().isVisible()) {
            screen.turnOff();
            safeController.setState(SafeState.OFF);
        } else {
            screen.turnOn();
            if (pinManager.isDefaultPIN()) {
                safeController.setState(SafeState.INITIAL_PIN_SETUP);
            } else {
                safeController.setState(SafeState.NORMAL);
            }
        }
    }
}
