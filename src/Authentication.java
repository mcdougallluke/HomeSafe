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
    private static List<AuthenticationPeriod> allowedPeriods = new ArrayList<>();

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
    }
}
