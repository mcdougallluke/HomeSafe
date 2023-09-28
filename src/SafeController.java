// CS 460 Team 01

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SafeController {

    private SafeState currentState;
    private Screen screen;
    private PINManager pinManager;
    private SafeGUI safeGUI;
    private List<User> users = new ArrayList<>();
    private User currentUser = null;


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
        System.out.println("Current state: " + currentState);
        switch (currentState) {
            case WAITING_FOR_IRIS -> handleWaitingForIris();
            case SETTING_IRIS -> handleSettingIris();
            case INITIAL_PIN_SETUP -> handleInitialPinSetup();
            case NORMAL -> handleNormalState();
            case CLOSED -> handleCloseSafe();
            case ADD_NEW_USER -> handleAddNewUser();
            case LOCKED -> handleLockedState();
        }

    }

    private void handleWaitingForIris() {
        screen.displayMessage("Waiting on Iris scan");
        // Additional setup logic here
    }
    private void handleSettingIris() {
        screen.displayMessage("Scan your Iris");
        // Additional setup logic here
    }
    
    private void handleInitialPinSetup() {
        screen.displayMessage("Enter Set up PIN");
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

    public void checkPIN(String enteredPIN) {
        if (currentState == SafeState.INITIAL_PIN_SETUP) {
            if ("00000".equals(enteredPIN)) {
                screen.displayMessage("Enter New PIN");
                setState(SafeState.SETTING_NEW_PIN);
            } else {
                screen.displayMessage("Wrong Setup PIN. Try again.");
            }
        } else if (currentState == SafeState.SETTING_NEW_PIN) {
            screen.displayMessage("Scan Your Iris");
            currentUser = new User(enteredPIN, null); // Save the entered PIN temporarily
            setState(SafeState.SETTING_IRIS); // New state to wait for the iris scan

        }
//        else if (currentState == SafeState.SETTING_IRIS) {
//            //setIrisForCurrentUser(scannedIrisName); // assuming scannedIrisName is the iris information you get after scanning
//            users.add(currentUser);   // Add the current user to your list of users.
//            currentUser = null;       // Reset current user.
//            setState(SafeState.NORMAL);
//        }
        else if (currentState == SafeState.NORMAL) {
            boolean pinMatchFound = false;

            for (User user : users) {
                if (user.getPin() != null && user.getPin().equals(enteredPIN)) {
                    currentUser = user;
                    pinMatchFound = true;
                    screen.displayMessage("Scan Your Iris");
                    setState(SafeState.WAITING_FOR_IRIS);
                    break;  // Exit the loop once a match is found
                }
            }

            if (!pinMatchFound) {
                screen.displayMessage("Wrong PIN");
            }
        }

    }

    public void resetUser(){
        currentUser = null;
    }

    public void checkIris(String irisName) {
        if (currentState == SafeState.WAITING_FOR_IRIS && currentUser != null) {
            if (currentUser.getIrisName().equals(irisName)) {
                handleUnlockedState();
            } else {
                screen.displayMessage("Wrong Iris");
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
}
