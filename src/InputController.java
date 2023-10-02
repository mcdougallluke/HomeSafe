// CS 460 Team 01

// Handles input from the user via the IRIS scanner and Keypad
public class InputController {
    private boolean isPoweredOn = false;
    private Screen screen;
    private SafeController safeController;
    private PINManager pinManager;

    public void setSafeController(SafeController safeController) {
        this.safeController = safeController;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setPINManager(PINManager pinManager) {
        this.pinManager = pinManager;
    }

    public void handleKeyInput(String key) {
        if(isPoweredOn) {
            screen.appendKeyEntry(key);
        }
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
            isPoweredOn = false;
        } else {
            screen.turnOn();
            if (safeController.usersIsEmpty() == true) {
                safeController.setState(SafeState.INITIAL_PIN_SETUP);
            } else {
                safeController.setState(SafeState.NORMAL);
            }
            isPoweredOn = true;
        }
    }

    public boolean isPoweredOn() {
        return isPoweredOn;
    }

}
