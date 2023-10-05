// CS 460 Team 01

import java.util.Timer;
import java.util.TimerTask;

public class Battery {
    private static final double FULL_CHARGE = 100.0;
    private static final int LOW_BATTERY_THRESHOLD = 20;

    // Amount of battery depletion per minute
    private static final double DEPLETION_AMOUNT = 100.0 / 2880; // Deplete 0.0347% per minute

    private double chargeLevel; // Battery charge level (0-100)
    private int remainingWorkingTime; // Remaining working time in minutes

    public Battery() throws InterruptedException {
        this.chargeLevel = FULL_CHARGE;
        this.remainingWorkingTime = 48 * 60; // 48 hours in minutes

        startBatteryDepletion(); // Starting battery depletion
    }
    // Method to start a timer which simulates battery depletion over time
    private void startBatteryDepletion() {
        Timer timer = new Timer();
        int depletionInterval = 60 * 1000; // Deplete every 1 minute
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (chargeLevel > 0) {
                    discharge(DEPLETION_AMOUNT);
                    chargeLevel -= DEPLETION_AMOUNT; // Decrement charge level
                    remainingWorkingTime -= 1; // Decrease remaining working time by 1 minute

                    if (remainingWorkingTime <= 0) {
                        System.out.println("Battery depleted. Safe operations are no longer possible.");
                        // stops the timer
                        timer.cancel();
                    }
                }

                if (chargeLevel <= 0) {
                    System.out.println("Battery depleted. Safe operations are no longer possible.");
                    timer.cancel();
                } else if (chargeLevel <= LOW_BATTERY_THRESHOLD) {
                    System.out.println("Low battery signal: Please recharge the safe.");
                }
            }
        }, depletionInterval, depletionInterval);
    }

    // Method to discharge the battery by a certain amount
    public void discharge(double amount) {
        if (amount >= 0 && amount <= chargeLevel) {
            chargeLevel -= amount;
        }
    }

    //Method to check if the provided voltage is within safe limits
    private boolean isVoltageSafe(int voltage) {
        // Check if the voltage is within a safe range
        return voltage >= 220 && voltage <= 240;
    }

    // Method to charge the battery by a certain amount, given a specific voltage
    public void charge(int amount, int voltage) { // To charge battery
        if (!isVoltageSafe(voltage)) {
            System.out.println("Warning: Charging with incorrect voltage!");
            return;
        }

        if (amount >= 0 && amount <= 100 - chargeLevel) {
            chargeLevel += amount;
            remainingWorkingTime += amount * 0.6;
        }
    }

    public boolean isLow() { // Check if battery is low
        return chargeLevel <= LOW_BATTERY_THRESHOLD;
    }

    public double getChargeLevel() { // Tells charge level
        return chargeLevel;
    }

    public int getRemainingWorkingTime() { // Returns remaining running time in minutes
        return remainingWorkingTime;
    }

    // Nested static Main class for testing the Battery class functionality
    public static class Main {
        public static void main(String[] args) {
            try {
                // Instantiate the Battery class
                Battery battery = new Battery();

                // Simulate using the battery for some time
                for (int i = 0; i < 70; i++) {
                    // Discharge the battery by 1% every minute (for demonstration purposes)
                    battery.discharge(1.0);

                    // Check if the battery is low and print a message
                    if (battery.isLow()) {
                        System.out.println("Battery is low! Please recharge.");
                    }

                    // Print the current charge level and remaining working time
                    System.out.println("Charge Level: " + battery.getChargeLevel() + "%");
                    System.out.println("Remaining Working Time: " + battery.getRemainingWorkingTime() + " minutes");

                    // Sleep for 1 minute (simulating the passage of time)
                    Thread.sleep(60000);
                }

            } catch (InterruptedException e) {
                // Handle interruption exception
                e.printStackTrace();
            }
        }
    }
}
