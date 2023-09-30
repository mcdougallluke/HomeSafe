// CS 460 Team 01

public class PINManager {
    private static final String DEFAULT_PIN = "000000";
    private static String currentPIN = DEFAULT_PIN;

    public static boolean checkPIN(String pin) {
        return currentPIN.equals(pin);
    }

    public static boolean setPIN(String pin) {
        if(pin.length() == 6) {
            currentPIN = pin;
            return true;
        }
        return false;
    }

    public boolean isDefaultPIN() {
        return currentPIN.equals(DEFAULT_PIN);
    }
}
