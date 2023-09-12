public class OffState implements SafeState {
    private Safe safe;

    public OffState(Safe safe) {
        this.safe = safe;
    }

    @Override
    public void handlePowerButton() {
        safe.setState(safe.getSetUpState());
    }

    @Override
    public boolean canProcessKey(char key) {
        return key == '*';
    }
}
