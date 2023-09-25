public class User {
    private String name; // Assuming every user has a name.
    private String pin;

    public User(String name, String pin) {
        this.name = name;
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    // You can also include getter and setter for the name, and other methods as needed.
}
