import java.util.Timer;
import java.util.TimerTask;

public class Battery {
    
    // Constants for the battery's charge
    private static final double FULL_CHARGE = 100.0;             // Maximum charge of the battery
    private static final int LOW_BATTERY_THRESHOLD = 20;         // Threshold below which the battery is considered low
    private static final double DEPLETION_AMOUNT = 0.5;          // Amount by which battery depletes each time

    private double chargeLevel;                                  // Current charge level of the battery
    private Timer timer;                                         // Timer to simulate battery discharge

    // Constructor: Initializes battery to full charge and sets up timer
    public Battery() {
        this.chargeLevel = FULL_CHARGE;                          // Set initial charge to full
        this.timer = new Timer();                                // Initialize the timer
    }

    // Start the battery discharge simulation
    public void start() {
        int depletionInterval = 60 * 100;                        // Time interval in ms for battery depletion
        timer.scheduleAtFixedRate(new TimerTask() {              // Schedule the discharge task
            @Override
            public void run() {
                if (chargeLevel > 0) {
                    discharge(DEPLETION_AMOUNT);                 // Discharge the battery by a fixed amount
                    System.out.println(chargeLevel);            // Print current battery level
                }
            }
        }, depletionInterval, depletionInterval);
    }

    // Stop the battery discharge simulation
    public void stop() {
        timer.cancel();                                          // Cancel the timer
        timer.purge();                                           // Remove all cancelled tasks from the timer's queue
        timer = new Timer();                                     // Reset the timer
    }

    // Discharge the battery by a given amount
    public void discharge(double amount) {
        if (amount >= 0 && amount <= chargeLevel) {              // Ensure valid discharge amount
            chargeLevel -= amount;                               // Reduce the charge level
        }
    }

    // Check if battery level is low
    public boolean isLow() {
        return chargeLevel <= LOW_BATTERY_THRESHOLD;             // Return true if charge level is at or below threshold
    }

    // Get the current charge level of the battery
    public double getChargeLevel() {
        return chargeLevel;                                      // Return the current charge level
    }
}
