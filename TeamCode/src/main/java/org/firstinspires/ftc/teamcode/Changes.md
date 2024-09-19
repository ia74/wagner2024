# Journal
This is the journal of changes. (manually recorded)

The latest entry will always be on the top.

## Format

# Date

- List of changes

## Entries

# 18 September, 2024

- Created Journal (Filled it with existing commits)
- Created NewGenGamepad
- - Which allows me to more easily handle controller input.
- Changed TeleOp to use this implementation of gamepad.
- Created Arm Mechanism
- Moved arm functionality from Hanger to Arm.
- Added some servos - elbow, wrist, shoulder
- Removed run() method from the Mechanism interface
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
