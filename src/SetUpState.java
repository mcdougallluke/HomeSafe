public class SetUpState implements SafeState {
    private final Safe safe;

    public SetUpState(Safe safe) {
        this.safe = safe;
    }

    @Override
    public void handlePowerButton() {
        safe.setState(safe.getOffState());
    }

    @Override
    public boolean canProcessKey(char key) {
        return true;
    }
}