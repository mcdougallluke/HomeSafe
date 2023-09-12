public class OffState implements SafeState {
    private Safe safe;
    private Screen screen;

    public OffState(Safe safe, Screen screen) {
        this.safe = safe;
        this.screen = screen;
    }

    @Override
    public void handlePowerButton() {
        screen.displayTempMessage("[SAFE ON]", 3);
        safe.setState(safe.getSetUpState());
    }

    @Override
    public boolean canProcessKey(char key) {
        return key == '*';
    }
}
