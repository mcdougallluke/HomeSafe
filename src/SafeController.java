// CS 460 Team 01

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SafeController {

    private SafeState currentState;
    private final Screen screen;
    private final SafeGUI safeGUI;
    private final Battery battery;
    private List<User> users = new ArrayList<>();
    private User currentUser = null;
    private boolean lockedOut = false;
    private int incorrectPinCount = 0;

    private static final String MASTER_PIN = "999999";



    public SafeController(Screen screen, SafeGUI safeGUI) {
        this.screen = screen;
        this.safeGUI = safeGUI;
        currentState = SafeState.OFF;
        battery = new Battery();
    }


    public void setState(SafeState newState) {
        currentState = newState;
        if (currentState == SafeState.OFF) {
            battery.stop();
        } else {
            battery.start();
        }
        switch (currentState) {
            case INITIAL_PIN_SETUP -> handleInitialPinSetup();
            case NORMAL -> handleNormalState();
            case CLOSED -> handleCloseSafe();
            case SETTING_NEW_PIN -> handleSettingNewPin();
            case LOCKED_OUT -> handleLockedOutState();
        }

    }


    private void handleInitialPinSetup() {
        screen.clearKeyEntry();
        screen.displayMessage("Enter Master PIN");
        checkBatteryLevel();
    }

    private void handleSettingNewPin() {
        screen.clearKeyEntry();
        screen.displayMessage("Enter New User PIN");
    }

    private void handleNormalState() {
        screen.clearKeyEntry();
        screen.displayMessage("Enter your PIN");
        checkBatteryLevel();
    }

    private void handleLockedOutState() {
        lockedOut = true;
        screen.displayMessage("[Locked Out]");
    }
    private boolean handleLockedOutState(String enteredPIN) {
        if (MASTER_PIN.equals(enteredPIN)) {
            incorrectPinCount = 0;
            lockedOut = false;
            setState(SafeState.NORMAL);
            return true;
        }
        screen.tempDisplayMessage("Wrong PIN");
        return false;
    }

    private void handleUnlockedState() {
        incorrectPinCount = 0;
        screen.displayMessage("Safe Unlocked");
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> safeGUI.openSafe());
        delay.play();
    }

    public void handleCloseSafe() {
        setState(SafeState.NORMAL);
    }


    public SafeState getCurrentState() {
        return currentState;
    }

    public void addUser(User user) {
        users.add(user);
    }


    public boolean checkPIN(String enteredPIN) {
        screen.clearKeyEntry();
        return switch (currentState) {
            case INITIAL_PIN_SETUP -> handleInitialPinSetup(enteredPIN);
            case SETTING_NEW_PIN -> handleSettingNewPin(enteredPIN);
            case LOCKED_OUT -> handleLockedOutState(enteredPIN);
            case NORMAL -> handleNormalState(enteredPIN);
            default -> false;
        };
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
        screen.displayMessage("Scan your Iris");
        currentUser = new User(enteredPIN, null);
        setState(SafeState.SETTING_IRIS);
        return true;
    }

    private boolean handleNormalState(String enteredPIN) {
        if (MASTER_PIN.equals(enteredPIN)) {
            setState(SafeState.SETTING_NEW_PIN);
            return true;
        }
        for (User user : users) {
            if (enteredPIN.equals(user.getPin())) {
                currentUser = user;
                screen.displayMessage("Scan your Iris");
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
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event -> {
                    incorrectPinCount++;
                    if (incorrectPinCount >= 3) {
                        setState(SafeState.LOCKED_OUT);
                    } else {
                        setState(SafeState.NORMAL);
                    }
                });
                delay.play();
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

    private void checkBatteryLevel() {
        if (battery.isLow()) {
            screen.tempDisplayMessage("LOW BATTERY [" + String.format("%.1f", battery.getChargeLevel()) + "%]");
        }

    }
}
