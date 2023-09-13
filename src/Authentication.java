import java.util.Random;
import java.util.Scanner;

public class Authentication {
    private static boolean locked;
    private static boolean unlocked;

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

    private static String generateOTP(int length) {
        int rand = (int)(Math.random()*Math.pow(10, length));
        return "" + rand;
    }

    private static boolean irisScan() {
        return true;
    }

    public static void main(String args[]) {
        if(locked == true) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to KNOX security");
        System.out.println("Enter password as step 1");
        System.out.println("Scan your iris and then enter OTP send on mail.");

        boolean flag = irisScan();
        if(flag) {
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