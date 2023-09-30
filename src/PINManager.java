// CS 460 Team 01

public class PINManager {
    private static final String DEFAULT_PIN = "000000";
    // The current PIN in use.
    private static String currentPIN = DEFAULT_PIN;

    // Checks if the provided PIN matches the current PIN.
    public static boolean checkPIN(String pin) {
        return currentPIN.equals(pin);
    }


    // Sets the current PIN to the provided value if it is of valid length.
    public static boolean setPIN(String pin) {
        if(pin.length() == 6) {
            currentPIN = pin;
            return true;
        }
        return false;
    }

    // Checks if the current PIN is set to the default value.
    public boolean isDefaultPIN() {
        return currentPIN.equals(DEFAULT_PIN);
    }
}
