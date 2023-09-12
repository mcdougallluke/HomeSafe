public class SetUpState implements SafeState {
    private final Safe safe;
    private String setupPin = "00000";
    private String userEnteredPin = "";
    private boolean awaitingNewPin = false;

    public SetUpState(Safe safe) {
        this.safe = safe;
    }

    @Override
    public void handlePowerButton() {
        safe.setState(safe.getOffState());
        reset();  // Reset state
    }

    @Override
    public boolean canProcessKey(char key) {
        userEnteredPin += key;
        if (awaitingNewPin) {
            if (userEnteredPin.length() == 5) {  // assuming pin length is 5
                // Save new pin
                // For simplicity, just printing it out. You may want to save it in a more secure way.
                System.out.println("New pin set: " + userEnteredPin);
                safe.setState(safe.getNormalState());
                safe.getScreen().displayMessage("Pin set successfully!");
                reset();
            }
        } else {
            if (userEnteredPin.equals(setupPin)) {
                awaitingNewPin = true;
                userEnteredPin = "";  // Reset the entered pin
                safe.getScreen().displayMessage("Enter new pin:");
            } else if (userEnteredPin.length() >= setupPin.length()) {
                safe.getScreen().displayMessage("Incorrect! Try again.");
                userEnteredPin = "";  // Reset the entered pin
            }
        }
        return true;
    }

    private void reset() {
        awaitingNewPin = false;
        userEnteredPin = "";
    }
}
