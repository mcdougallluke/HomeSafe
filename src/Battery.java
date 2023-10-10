// CS 460 Team 01

import java.util.Timer;
import java.util.TimerTask;

public class Battery {
    private static final double FULL_CHARGE = 100.0;
    private static final int LOW_BATTERY_THRESHOLD = 20;
    private static final double DEPLETION_AMOUNT = 0.5;

    private double chargeLevel;
    private Timer timer;

    public Battery() {
        this.chargeLevel = FULL_CHARGE;
        this.timer = new Timer();
    }

    public void start() {
        int depletionInterval = 60 * 100;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (chargeLevel > 0) {
                    discharge(DEPLETION_AMOUNT);
                }
            }
        }, depletionInterval, depletionInterval);
    }

    public void stop() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
    }

    public void discharge(double amount) {
        if (amount >= 0 && amount <= chargeLevel) {
            chargeLevel -= amount;
        }
    }

    public boolean isLow() {
        return chargeLevel <= LOW_BATTERY_THRESHOLD;
    }

    public double getChargeLevel() {
        return chargeLevel;
    }
}
