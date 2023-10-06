// CS 460 Team 01

import javafx.application.Platform;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Battery {
    private static final double FULL_CHARGE = 19.0;
    private static final int LOW_BATTERY_THRESHOLD = 20;
    private static final double DEPLETION_AMOUNT = 100.0/2880;

    private double chargeLevel;
    private int remainingWorkingTime;

    private BatteryListener listener;
    private final Label batteryLabel = new Label();

    private ArrayList<BatteryListener> listeners = new ArrayList<>();

    public void addBatteryListener(BatteryListener listener) {
        listeners.add(listener);
    }

    private void notifyLowBattery() {
        for (BatteryListener listener : listeners) {
            listener.onLowBattery();
        }
    }

    public Battery() throws InterruptedException {
        this.chargeLevel = FULL_CHARGE;
        this.remainingWorkingTime =  48 * 60; // 48 hours in minutes

        startBatteryDepletion(); // Starting battery depletion
    }

    private void startBatteryDepletion() {
        Timer timer = new Timer();
        int depletionInterval =  60*100; // Deplete every 1 minute
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (chargeLevel > 0) {
                    // Update the UI
                    Platform.runLater(() -> {
                        batteryLabel.setText("Charge Level: " + chargeLevel + "%");
                    });
                    discharge(DEPLETION_AMOUNT);
                    chargeLevel -= DEPLETION_AMOUNT; // Decrement charge level
                    remainingWorkingTime -= 1; // Decrease remaining working time by 1 minute
                    if (chargeLevel <= LOW_BATTERY_THRESHOLD && listener != null) {
                        System.out.println("Low battery The signal: Please recharge the safe.");
                    }

                    if (remainingWorkingTime <= 0) {
                        System.out.println("Battery depleted. Safe operations are no longer possible.");
                        timer.cancel();
                    }
                }

                if (chargeLevel <= 0) {
                    System.out.println("Battery depleted. Safe operations are no longer possible.");
                    timer.cancel();
                } else if (chargeLevel <= LOW_BATTERY_THRESHOLD) {
                    System.out.println("Low battery signal The: Please recharge the safe.");
                    Platform.runLater(() -> {
                        notifyLowBattery();
                    });
                }
            }
        }, depletionInterval, depletionInterval);
    }

    public void discharge(double amount) {
        if (amount >= 0 && amount <= chargeLevel) {
            chargeLevel -= amount;
        }
    }

    private boolean isVoltageSafe(int voltage) {
        // Check if the voltage is within a safe range
        return voltage >= 220 && voltage <= 240;
    }

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

    public void setBatteryListener(BatteryListener listener) {
        this.listener = listener;
    }

    // Nested Main class for testing
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
