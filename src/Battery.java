import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class Battery {
    private static final double FULL_CHARGE = 100.0;
    private static final int LOW_BATTERY_THRESHOLD = 20;
    private static final double DEPLETION_AMOUNT = 100.0 / 2880; // Deplete 0.0347% per minute

    private double chargeLevel; // Battery charge level (0-100)
    private int remainingWorkingTime; // Remaining working time in minutes

    public Battery() throws InterruptedException {
        this.chargeLevel = FULL_CHARGE;
        this.remainingWorkingTime = 48 * 60; // 48 hours in minutes

        startBatteryDepletion(); // Starting battery depletion

//        sleep(70000);
//        System.out.println(getRemainingWorkingTime());
//        System.out.println(getChargeLevel());

    }

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
}