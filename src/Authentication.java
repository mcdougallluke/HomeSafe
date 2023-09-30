// CS 460 Team 01

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

// This class handles user authentication based on multiple methods
public class Authentication {
    private static boolean locked; // Entering wrong password multiple times locks the device
    private static boolean unlocked;
    // List of periods during which authentication is allowed.
    private static List<AuthenticationPeriod> allowedPeriods = new ArrayList<>();
    // Constructor initializes locked and unlocked states.
    public Authentication() {
        locked = false;
        unlocked = false;
    }

    // Getter method to check if the system is unlocked
    public static boolean isUnlocked() {
        return unlocked;
    }

    public static void setUnlocked(boolean unlocked) {
        Authentication.unlocked = unlocked;
    }

    // Method to add a new allowed period for authentication
    public static void addAllowedPeriod(int startHour, int endHour) {
        allowedPeriods.add(new AuthenticationPeriod(startHour, endHour));
    }

    public static void clearAllowedPeriods() {
        allowedPeriods.clear();
    }

    private static String generateOTP(int length) {
        int rand = (int)(Math.random()*Math.pow(10, length));
        return "" + rand;
    }

    // Mock method to simulate an iris scan. Always returns true for this implementation.
    private static boolean irisScan() {
        return true;
    }

    // Checks if authentication is allowed at the current time true if authentication is allowed, false otherwise.
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

    // Main method to execute the authentication process.
    public static void main(String args[]) {
        if (locked) {
            return;
        }

        // Set default allowed authentication period from 9 AM to 9 PM.
        addAllowedPeriod(9, 21);

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to KNOX security");

        if (!isAuthenticationAllowed()) {
            System.out.println("Authentication is not allowed at this time.");
            return;
        }

        System.out.println("Enter password as step 1");
        System.out.println("Scan your iris and then enter OTP sent on mail.");

        boolean flag = irisScan();
        if (flag) {
            System.out.println("Iris scan successful.");
        } else {
            System.out.println("Iris scan failed.");
            return;
        }

        System.out.println("Enter the PIN: ");

        long startTime = System.currentTimeMillis(); // Record the start time
        String enteredOTP = sc.next();
        long endTime = System.currentTimeMillis(); // Record the end time

        // Check if OTP was entered within 3 seconds
        if (endTime - startTime > 3000) {
            System.out.println("OTP entry time limit exceeded.");
            System.exit(1); // Exit the program
        }

        if (PINManager.checkPIN(enteredOTP)) {
            System.out.println("OTP authentication successful!");
            System.out.println("Press 1 to change pin or 2 to cancel.");
            int ch = sc.nextInt();

            if(ch == 1) {
                String s = sc.next();
                PINManager.setPIN(s);
            }
        } else {
            System.out.println("OTP authentication failed!");
        }
    }
}
// This class defines periods (start and end hour) during which authentication is allowed.
class AuthenticationPeriod {
    private int startHour;
    private int endHour;

    public AuthenticationPeriod(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }
    // Getters for start and end hours.

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
