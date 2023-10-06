# HomeSafe CS460 Team 01

## Members:
- Marina Seheon (Manager)
- Andrei Phelps (Document Manager)
- Luke McDougall (Lead Software Engineer)
- Spoorthi Menta
- Vamsi Krishna Singara
- Jack Vanlyssel

## How to Run the Program:

### Prerequisites:
1. Ensure you have a Java Development Kit (JDK) installed that supports JavaFX.
2. Ensure you have a Java Integrated Development Environment (IDE) like Eclipse, IntelliJ IDEA, or any other of your choice.

### Steps:
1. **Clone the Repository**:
   ```git clone https://github.com/mcdougallluke/HomeSafe```
2. **Open the Project in your IDE**:
3. **Run the Program**:
   - Run our main method in the `SafeGUI.java` file.

### Set Up:
The user has just acquired their new HomeSafe and wishes to initialize and configure their access credentials before turning off the safe for subsequent use.
1. The user removes the HomeSafe from the packaging and places the safe in their desired location.
2. The user removes the manual containing the Master PIN from the packaging.
3. The User presses the power button on the keypad to power the system on.
   • The battery power from the primary power source activates the safe components.
   • The microcontroller is activated and awaiting input.
   • The keypad is activated and awaiting input.
   • The LED display prompts the user to enter a 6-digit Master PIN.
5. The user enters a 6-digit Master PIN and presses ENTER.
   • The authentication manager verifies the master PIN.
   • The LED display prompts the user to enter a new 6-digit PIN.
   • The keypad awaits further input from the user.
7. The user inputs a 6-digit PIN they wish to use and presses ENTER.
   • The LED display prompts the user to re-enter the 6-digit PIN.
   • The keypad awaits further input from the user.
9. The user positions their eye before the Iris Scanner until they hear a ”BEEP.”
   • The LED Display prompts the user to verify the iris scan (repeat the previous process).
   • The iris scanner awaits further input from the user.
11. The user presses the POWER button on the keypad to power down HomeSafe.
   • The power provided by the primary power supply is halted.
   • The microcontroller and all other safe components are no longer active.

### Authorization:
In this scenario, the user has previously configured their iris scan and PIN and now seeks to access the safe utilizing those credentials.
1. The user presses the power button on the keypad to power the system on.
   • The battery power from the primary power source activates the safe components.
   • The microcontroller is activated and awaiting input.
   • The keypad is activated and awaiting input.
   • The LED display prompts the user to enter a 6-digit master PIN.
2. The user enters their PIN and presses ENTER.
   The authentication manager verifies the PIN.
   • The LED display prompts the user to provide an iris scan.
   • Iris Scanner becomes active and waits for user input.
3. The user positions their eye before the iris scanner until they hear a ”BEEP.”
   • The authentication Manager verifies the iris scan.
   • The LED displays ”Authentication Successful” to the user.
   • The locking mechanism status is changed to UNLOCKED.
4. The user opens the safe.
   • The InputController is disabled.
   • The LED displays ”Door Open” to the user.
   • The timer starts. An alarm starts if the door is open for more than 120 seconds.
5. The user closes the safe.
   • The InputController is re-enabled.
   • The LED displays ”Door Closed” to the user.
   • The timer starts. The safe powers itself down if the door is closed for more than 120 seconds without user input on the keypad.
6. The user presses the POWER button on the keypad to power down HomeSafe.
   • The power provided by the primary power supply is halted.
   • The microcontroller and all other safe components are no longer active.
