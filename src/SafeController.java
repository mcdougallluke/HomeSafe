public class SafeController {

    private SafeState currentState;
    private Screen screen;
    private PINManager pinManager;
    private SafeGUI safeGUI;


    public SafeController(Screen screen, SafeGUI safeGUI) {
        this.screen = screen;
        this.safeGUI = safeGUI;
        currentState = SafeState.OFF;
    }


    public void setPINManager(PINManager pinManager) {
        this.pinManager = pinManager;
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
        System.out.println("Current state: " + currentState);
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
        safeGUI.openSafe();
    }

    public void handleCloseSafe() {
        currentState = SafeState.NORMAL;
        safeGUI.closeSafe();
        handleNormalState();
    }


    private void handleLockedState() {
        screen.displayMessage("Safe Locked");
        // Logic for locked state
    }

    public SafeState getCurrentState() {
        return currentState;
    }

    public void checkPIN(String enteredPIN) {
        if (currentState == SafeState.INITIAL_PIN_SETUP) {
            if ("00000".equals(enteredPIN)) {
                screen.displayMessage("Enter New PIN");
                currentState = SafeState.SETTING_NEW_PIN;
            } else {
                screen.displayMessage("Wrong Setup PIN. Try again.");
            }
        } else if (currentState == SafeState.SETTING_NEW_PIN) {
            pinManager.setPIN(enteredPIN);
            screen.displayMessage("PIN Set Successfully");
            currentState = SafeState.NORMAL;
        } else if (currentState == SafeState.NORMAL) {
            if (pinManager.checkPIN(enteredPIN)) {
                handleUnlockedState();
            } else {
                screen.displayMessage("Wrong PIN");
            }
        }
    }

}
