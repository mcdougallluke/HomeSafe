// CS 460 Team 01

public class PINManager {
    private static final String DEFAULT_PIN = "000000";
    private String currentPIN = DEFAULT_PIN;

    public boolean checkPIN(String pin) {
        return currentPIN.equals(pin);
    }

    public void setPIN(String pin) {
        this.currentPIN = pin;
    }

    public boolean isDefaultPIN() {
        return currentPIN.equals(DEFAULT_PIN);
    }
}
