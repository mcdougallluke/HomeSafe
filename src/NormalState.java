public class NormalState implements SafeState {
    private final Safe safe;

    public NormalState(Safe safe) {
        this.safe = safe;
    }

    @Override
    public void handlePowerButton() {
        // Handle power button when the safe is in NORMAL mode
        // Perhaps you might want to shut down the safe
        safe.setState(safe.getOffState());
    }

    @Override
    public boolean canProcessKey(char key) {
        return true;
    }
}