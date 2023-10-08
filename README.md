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

### PIN Timeout
In this scenario, the user has previously configured their PIN and Iris Scan and now seeks to open the safe. However, the user waits too long between keypad inputs, resulting in a system timeout.
1. The user presses the power button on the keypad to power the system on.
   •  Battery power from the primary power source activates the safe components.
   • The microcontroller is activated and awaiting input.
   • The keypad is activated and awaiting input.
   • The LED display prompts users to enter their 6-digit PIN.
2. The user begins to enter their PIN but hesitates or is interrupted.
   • The LED display shows the numbers the user has entered so far.
   • An internal timeout counter starts when the first key is pressed.
3. After a pause of more than 3 seconds without pressing a new key or hitting the enter button, the user’s input is automatically cleared due to a TIMEOUT.
   • The authentication manager resets PIN input.
   • The LED display prompts users to enter their 6-digit PIN.
   • The keypad awaits further input from the user.
4. After three consecutive TIMEOUT lockouts, the system enters the ”LOCKED OUT” state.
   • The authentication manager resets PIN input.
   • The LED display prompts the user to enter the 6-digit master PIN.
   • The keypad awaits further input from the user.
5. Upon entering the master PIN when prompted, the system is taken out of the ”LOCKED OUT” state, and the user can enter the AUTHORIZATION process once again.
   • The keypad is activated and awaiting input.
   • The LED display prompts users to enter their 6-digit PIN.

### Unauthorized Access (PIN)
In this scenario, the user has previously configured their PIN and iris scan and now seeks to open the safe. However, after three attempts, the user cannot enter their correct PIN and is prompted for the master PIN as an override.
1. The user presses the power button on the keypad to power the system on.
   • The battery power from the primary power source activates the safe components.
   • The microcontroller is activated and awaiting input.
   • The keypad is activated and awaiting input.
   • The LED display prompts users to enter their 6-digit PIN.
2. The user enters an incorrect PIN and presses ENTER.
   • The authentication manager attempts to verify the PIN.
   • The PIN Verification fails.
   • The LED display prompts users to enter their 6-digit PIN.
3. After three consecutive failed PIN verifications, the system enters the ”LOCKED OUT” state.
   • The authentication manager resets PIN input.
   • The LED display prompts the user to enter the 6-digit Master PIN.
   • The keypad awaits further input from the user.
4. Upon entering the master PIN when prompted, the system is taken out of the ”LOCKED OUT” state, and the user can enter the AUTHORIZATION process once again.
   • The keypad is activated and awaiting input.
   • The LED display prompts users to enter their 6-digit PIN.

### Unauthorized Access (Iris Scan)
In this scenario, the user has previously configured their PIN and Iris Scan and now seeks to open the safe. However, after three attempts, the user cannot provide a recognized iris scan and is prompted for the master PIN as an override.
1. The user presses the power button on the keypad to power the system on.
   • The battery power from the primary power source activates the safe components.
   • The microcontroller is activated and awaiting input.
   • The keypad is activated and awaiting input.
   • The LED display prompts users to enter their 6-digit PIN.
2. The user enters their PIN and presses ENTER.
   • The authentication manager verifies the PIN.
   • The LED display prompts the user to provide an iris scan.
   • The iris scanner becomes active and waits for user input.
3. The user provides an invalid Iris Scan.
   • The authentication manager verifies the iris scan.
   • The iris scan verification fails.
   • The LED display prompts the user to provide an iris scan.
4. After three consecutive failed iris scan verifications, the system enters the ”LOCKED OUT” state.
   • The authentication manager resets the AUTHORIZATION PROCESS.
   • The LED display prompts the user to enter the 6-digit master PIN.
   • The keypad awaits further input from the user.
5. Upon entering the master PIN when prompted, the system is taken out of the ”LOCKED OUT” state, and the user can enter the AUTHORIZATION process once again.
   • The keypad is activated and awaiting input.
   • The LED display prompts users to enter their 6-digit PIN.
