// CS 460 Team 01

public class User {
    private String irisName;
    private String pin;

    public User(String pin, String name) {
        this.pin = pin;
        this.irisName = name;

    }

    public String getPin() {
        return pin;
    }
    public String getIrisName() {
        return irisName;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setIrisName(String name) {
        this.irisName = name;
    }
}
