// CS 460 Team 01

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SafeController {

    private SafeState currentState;
    private Screen screen;
    private SafeGUI safeGUI;
    private List<User> users = new ArrayList<>();
    private User currentUser = null;
    private boolean lockedOut = false;
    private int incorrectPinCount = 0;
    private static final String MASTER_PIN = "999999";
    private Battery battery;
    private EyeButtons eyeButtons;

    public void initializeBatteryListener() {
        battery.addBatteryListener(() -> {
            screen.forceDisplayMessage("Low Battery!"); // This line displays the temporary message.
        });
    }

    void setBattery(Battery battery){
        this.battery = battery;
    }

    public void setEyeButtons(EyeButtons eyeButtons) {
        this.eyeButtons = eyeButtons;
    }


    public SafeController(Screen screen, SafeGUI safeGUI) {
        this.screen = screen;
        this.safeGUI = safeGUI;
        currentState = SafeState.OFF;
    }


    public void setState(SafeState newState) {
        currentState = newState;
        System.out.println("Current state: " + currentState);
        switch (currentState) {
            case WAITING_FOR_IRIS -> handleWaitingForIris();
            case SETTING_IRIS -> handleSettingIris();
            case INITIAL_PIN_SETUP -> handleInitialPinSetup();
            case NORMAL -> handleNormalState();
            case CLOSED -> handleCloseSafe();
            case LOCKED -> handleLockedState();
            case SETTING_NEW_PIN -> handleSettingNewPin();
            case LOCKED_OUT -> handleLockedOutState();
        }

    }

    private void handleWaitingForIris() {
        screen.displayMessage("Waiting on Iris scan");
    }
    private void handleSettingIris() {
        screen.displayMessage("Scan your Iris");
    }
    
    private void handleInitialPinSetup() {
        screen.displayMessage("Enter Master PIN");
    }

    private void handleSettingNewPin() {
        screen.displayMessage("Enter New User PIN");
    }

    private void handleNormalState() {
        screen.displayMessage("Enter your PIN");
    }

    private void handleLockedOutState() {
        lockedOut = true;
        screen.displayMessage("[Locked Out]");
    }
    private boolean handleLockedOutState(String enteredPIN) {
        if (MASTER_PIN.equals(enteredPIN)) {
            incorrectPinCount = 0;
            setState(SafeState.NORMAL);
            return true;
        }
        screen.tempDisplayMessage("Wrong PIN");
        return false;
    }

    private void handleUnlockedState() {
        screen.displayMessage("Safe Unlocked");
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> safeGUI.openSafe());
        delay.play();
    }

    public void handleCloseSafe() {
        setState(SafeState.NORMAL);
    }


    private void handleLockedState() {
        screen.displayMessage("Safe Locked");
    }

    public SafeState getCurrentState() {
        return currentState;
    }

    public void addUser(User user) {
        users.add(user);
    }


    public boolean checkPIN(String enteredPIN) {
        return switch (currentState) {
            case INITIAL_PIN_SETUP -> handleInitialPinSetup(enteredPIN);
            case SETTING_NEW_PIN -> handleSettingNewPin(enteredPIN);
            case LOCKED_OUT -> handleLockedOutState(enteredPIN);
            case NORMAL -> handleNormalState(enteredPIN);
            default -> false;
        };
    }
    public void forgotPassword() {
        if (currentUser != null) {
            // Remove the currentUser from the users list
            users.remove(currentUser);

            // Clear the currentUser's PIN and iris scan
            currentUser.setPin(null);
            currentUser.setIrisName(null);

            // Set currentUser to null
            currentUser = null;
        }
    }
    private boolean handleInitialPinSetup(String enteredPIN) {
        if (MASTER_PIN.equals(enteredPIN)) {
            setState(SafeState.SETTING_NEW_PIN);
            return true;
        }
        screen.tempDisplayMessage("Wrong PIN");
        return false;
    }

    private boolean handleSettingNewPin(String enteredPIN) {
        if (MASTER_PIN.equals(enteredPIN)) {
            screen.tempDisplayMessage("Cannot use Master PIN");
        }
        if (pinExists(enteredPIN)) {
            screen.tempDisplayMessage("PIN already in use");
            return false;
        }
        screen.displayMessage("Scan Your Iris");
        currentUser = new User(enteredPIN, null);
        setState(SafeState.SETTING_IRIS);
        return true;
    }

    private boolean handleNormalState(String enteredPIN) {
        if (MASTER_PIN.equals(enteredPIN)) {
            forgotPassword();
            setState(SafeState.SETTING_NEW_PIN);
            return true;
        }
        for (User user : users) {
            if (enteredPIN.equals(user.getPin())) {
                currentUser = user;
                screen.displayMessage("Scan Your Iris");
                setState(SafeState.WAITING_FOR_IRIS);
                return true;
            }
        }
        screen.tempDisplayMessage("Wrong PIN");
        incorrectPinCount++;
        if (incorrectPinCount >= 3) {
            setState(SafeState.LOCKED_OUT);
        }
        return false;
    }


    public void resetUser(){
        currentUser = null;
    }

    public void checkIris(String irisName) {
        if (currentState == SafeState.WAITING_FOR_IRIS && currentUser != null) {
            if (currentUser.getIrisName().equals(irisName)) {
                handleUnlockedState();
            } else {
                screen.tempDisplayMessage("Wrong Iris");
                incorrectPinCount++;
                setState(SafeState.NORMAL);
            }
        }
    }

    public void setIrisForCurrentUser(String irisName) {
        if(currentUser != null) {
            currentUser.setIrisName(irisName);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean usersIsEmpty() {
        return users.isEmpty();
    }

    // Helper method to check if a PIN already exists
    private boolean pinExists(String pin) {
        for (User user : users) {
            if (pin.equals(user.getPin())) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if an iris scan already exists
    boolean irisScanExists(String irisName) {
        for (User user : users) {
            if (irisName.equals(user.getIrisName())) {
                return true;
            }
        }
        return false;
    }

    public Screen getScreen() {
        return this.screen;
    }

    public boolean isLockedOut() {
        return lockedOut;
    }
}
