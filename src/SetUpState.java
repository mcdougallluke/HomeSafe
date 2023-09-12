public class SetUpState implements SafeState {
    private final Safe safe;

    public SetUpState(Safe safe) {
        this.safe = safe;
    }

    @Override
    public void handlePowerButton() {
        // Transition to NORMAL state or perhaps OFF state
        safe.setState(safe.getOffState());
        // Or any other logic when power button is pressed during setup
    }

    @Override
    public boolean canProcessKey(char key) {
        return true;
    }
}