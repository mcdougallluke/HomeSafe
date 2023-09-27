import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Authentication {
    private static boolean locked; // Entering the wrong PIN multiple times locks the device
    private static boolean unlocked;
    private static List<AuthenticationPeriod> allowedPeriods = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    public Authentication() {
        locked = false;
        unlocked = false;
    }

    public static boolean isUnlocked() {
        return unlocked;
    }

    public static void setUnlocked(boolean unlocked) {
        Authentication.unlocked = unlocked;
    }

    public static void addAllowedPeriod(int startHour, int endHour) {
        allowedPeriods.add(new AuthenticationPeriod(startHour, endHour));
    }

    public static void clearAllowedPeriods() {
        allowedPeriods.clear();
    }

    public static void addUser(String pin, String irisName) {
        users.add(new User(pin, irisName));
    }

    public static User getUser(String irisName) {
        for (User user : users) {
            if (user.getIrisName().equals(irisName)) {
                return user;
            }
        }
        return null;
    }

    private static String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randDigit = (int) (Math.random() * 10);
            otp.append(randDigit);
        }
        return otp.toString();
    }

    private static boolean irisScan() {
        // Simulate iris scan
        return true;
    }

    public static boolean isAuthenticationAllowed() {
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);

        for (AuthenticationPeriod period : allowedPeriods) {
            if (currentHour >= period.getStartHour() && currentHour <= period.getEndHour()) {
                return true;
            }
        }

        return false;
    }

    public static void main(String args[]) {
        if (locked) {
            return;
        }

        // Default allowed authentication from 9 AM to 9 PM
        addAllowedPeriod(9, 21);

        // Add some users for testing
        addUser("1234", "JohnDoe");
        addUser("5678", "AliceSmith");

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to KNOX security");

        if (!isAuthenticationAllowed()) {
            System.out.println("Authentication is not allowed at this time.");
            return;
        }

        System.out.print("Enter your PIN as step 1: ");
        String pin = sc.next();
        System.out.print("Enter your iris name as step 2: ");
        String irisName = sc.next();

        User user = getUser(irisName);

        if (user != null && pin == user.getPin()) {
            System.out.println("Authentication successful!");
            // Unlock the device
            setUnlocked(true);

            // Ask the user if they want to change their PIN
            System.out.print("Do you want to change your PIN? (yes/no): ");
            String changePinChoice = sc.next().toLowerCase();

            if (changePinChoice.equals("yes")) {
                System.out.print("Enter your new PIN: ");
                String newPin = sc.next();
                user.setPin(newPin);
                System.out.println("PIN changed successfully!");
            }
        } else {
            System.out.println("Authentication failed!");
        }

        if (isUnlocked()) {
            boolean flag = irisScan();
            if (flag) {
                System.out.println("Iris scan successful.");
            } else {
                System.out.println("Iris scan failed.");
                return;
            }

            String otp = generateOTP(6);
            System.out.println("Enter the OTP: ");
            System.out.println(otp);
            String enteredOTP = sc.next();

            if (otp.equals(enteredOTP)) {
                System.out.println("OTP authentication successful!");
            } else {
                System.out.println("OTP authentication failed!");
            }
        }
    }
}

class AuthenticationPeriod {
    private int startHour;
    private int endHour;

    public AuthenticationPeriod(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }//endhour
}