# Journal
This is the journal of changes. (manually recorded)

The latest entry will always be on the top.

## Format

# Date

- List of changes

## Entries

# 02 October, 2024

- Added descriptions to the tests & fixed Arm's sleep not properly running
- Created a test for motors

# 30 September, 2024

- Claw has some helper methods now to make moving a little more verbose & readable
- Remove all dead wheels & their references
- Add sleeps to all tests [Claw, Arm] so we can watch the test happening in realtime

# 25 September, 2024

- Removed a dead wheel
- Set the two wheel localizer to actually work

# 23 September, 2024

- Add claw.wrist
- Changed claw to use the new design (1 servo)
- Arm now included in TeleOp
- Claw now holds its open position
- Claw is now a positional servo
- Gamepads now create their own pose (on-demand) for TeleOp driving
- TestClaw has been implemented
- TestArm has been implemented + added
- TestRoadRunner has been implemented + added
- Added tests to TestRegistrar
- Tests now use their own pose

# 18 September, 2024

- Created Journal (Filled it with existing commits)
- Created NewGenGamepad
- - Which allows me to more easily handle controller input.
- Changed TeleOp to use this implementation of gamepad.
- Created Arm Mechanism
- Moved arm functionality from Hanger to Arm.
- Added some servos - elbow, wrist, shoulder
- Removed run() method from the [NGTeleOp.java](wagner%2Fopmodes%2FNGTeleOp.java)Mechanism interface
- Moved all previously existing run() code into the TeleOp

# 16 September, 2024

- Hanger Mechanism has less extraneous code (Moved repetitive code into a reusable method.)
- Consolidate HardwareMap references into PartsMap enum
- Remove TankDrive (We use Mecanum drive)
- Add more MechanismStates for verbosity/readability.
- - States  like Unextended are the same value as Closed - just for readability.
- Created a new test for the Hanger Mechanism

# 11 September, 2024

- Implemented MechanismState for measuring status of mechanisms 
- Created Hanger Mechanism 
- Changed Claw servo from Positional to Continuous
- Added start/stop method
- Created code for Claw TeleOp operation

# 9 September, 2024

- Created initial Claw class with basic Servo setup & methods.
- Created TeleOp with basic gamepad driving

# 7 September, 2024

- Created repository
- Made basic structure for Mechanisms
- Created Test OpMode
- Made a OpMode Registrar (handle showing tests when needed)
- Mentored Kitty Hawk MS about general Road Runner operations
