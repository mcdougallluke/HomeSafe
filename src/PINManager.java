// CS 460 Team 01

import java.util.ArrayList;
import java.util.List;

public class PINManager {
    private static final String DEFAULT_PIN = "000000";
    private List<User> users = new ArrayList<>();

    public boolean checkPIN(String pin) {
        return getUserByPIN(pin) != null;
    }

    public User getUserByPIN(String pin) {
        for (User user : users) {
            if (pin.equals(user.getPin())) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean pinExists(String pin) {
        return getUserByPIN(pin) != null;
    }

    public boolean isDefaultPIN(String pin) {
        return DEFAULT_PIN.equals(pin);
    }
}

