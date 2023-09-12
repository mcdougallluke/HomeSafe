public interface SafeState {
    void handlePowerButton();
    boolean canProcessKey(char key);
}
