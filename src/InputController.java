// CS 460 Team 01

// This class is responsible for handling user input events like keypad presses and IRIS scans.
public class InputController {

    // Variable to check if the device is powered on
    private boolean isPoweredOn = false;
    // Reference to the screen, to interact with the display
    private Screen screen;
    // Reference to the main SafeController
    private SafeController safeController;

    // Maximum number of characters allowed for PIN entry
    private static final int MAX_PIN_LENGTH = 6;

    // Setter method to assign the SafeController
    public void setSafeController(SafeController safeController) {
        this.safeController = safeController;
    }

    // Setter method to assign the Screen
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    // Handle the input from the keypad
    public void handleKeyInput(String key) {
        // Append key input if device is powered on and current input length is less than max PIN length
        if(isPoweredOn && screen.getCurrentKeyEntry().length() < MAX_PIN_LENGTH) {
            screen.appendKeyEntry(key);
        }
    }

    // Handle the "Enter" button press
    public void handleEnterButton() {
        // Stop any ongoing timeout, and check the entered PIN if it has the required length
        screen.stopTimeout();
        String enteredPin = screen.getCurrentKeyEntry();
        if(enteredPin.length() == MAX_PIN_LENGTH) {
            boolean isPinCorrect = safeController.checkPIN(enteredPin);
            if(isPinCorrect) {
                screen.stopTimeout();
            }
            screen.clearKeyEntry(); // Clear the entered PIN after checking
        }
    }

    // Handle the "Cancel" button press, removing the last entered digit/character
    public void handleCancel() {
        screen.removeLastKeyEntry();
    }

    // Handle the power button, toggling between the ON and OFF states of the device
    public void handlePowerButton() {
        if (screen.getScreenComponent().isVisible()) {
            screen.turnOff();  // Turn off the screen
            safeController.setState(SafeState.OFF); // Set the state to OFF
            isPoweredOn = false;
        } else {
            screen.turnOn(); // Turn on the screen
            // Decide the state based on the conditions
            if (safeController.isLockedOut()) {
                safeController.setState(SafeState.LOCKED_OUT);
            } else if (safeController.usersIsEmpty()) {
                safeController.setState(SafeState.INITIAL_PIN_SETUP);
            } else {
                safeController.setState(SafeState.NORMAL);
            }
            isPoweredOn = true;
        }
    }

    // Method to check if the device is powered on
    public boolean isPoweredOn() {
        return isPoweredOn;
    }
}
