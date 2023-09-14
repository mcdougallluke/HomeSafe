public class SafeController {

    private SafeState currentState;
    private Screen screen;

    public SafeController(Screen screen) {
        this.screen = screen;
        currentState = SafeState.INITIAL_PIN_SETUP; // Start with the initial setup
    }

    public void setState(SafeState newState) {
        currentState = newState;

        switch (currentState) {
            case INITIAL_PIN_SETUP -> handleInitialPinSetup();
            case NORMAL -> handleNormalState();
            case ADD_NEW_USER -> handleAddNewUser();
            case UNLOCKED -> handleUnlockedState();
            case LOCKED -> handleLockedState();
        }
    }

    private void handleInitialPinSetup() {
        screen.displayMessage("Set up PIN");
        // Additional setup logic here
    }

    private void handleNormalState() {
        screen.displayMessage("Enter PIN");
        // Normal state logic here
    }

    private void handleAddNewUser() {
        screen.displayMessage("Add User");
        // Logic to add new user here
    }

    private void handleUnlockedState() {
        screen.displayMessage("Safe Unlocked");
        // Logic for unlocked state
    }

    private void handleLockedState() {
        screen.displayMessage("Safe Locked");
        // Logic for locked state
    }

    public SafeState getCurrentState() {
        return currentState;
    }

    // Other functions related to safe operations can go here
}
