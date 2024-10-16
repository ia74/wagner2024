package org.firstinspires.ftc.teamcode.wagner.nggamepad;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepadPressables.Button;

public class NGGamepad {
    public Gamepad gamepad;
    public NGGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public PoseVelocity2d driveWithThis() {
        return new PoseVelocity2d(
                new Vector2d(
                        -this.left_stick_y(),
                        -this.left_stick_x()
                ),
                -this.right_stick_x()
        );
    }

    public boolean is(Button btn) {return isPressed(btn);}
    public boolean is(NGGamepadPressables.Trigger btn) {return isTrigger(getTrigger(btn));}

    public float getTrigger(NGGamepadPressables.Trigger trigger) {
        float val = 0;
        switch(trigger) {
            case LEFT_TRIGGER:
                val = gamepad.left_trigger;
                break;
            case RIGHT_TRIGGER:
                val = gamepad.right_trigger;
                break;
        }
        return val;
    }

    public boolean isPressed(Button button) {
        boolean buttonValue = false;
        switch (button) {
            case A:
                buttonValue = gamepad.a;
                break;
            case B:
                buttonValue = gamepad.b;
                break;
            case X:
                buttonValue = gamepad.x;
                break;
            case Y:
                buttonValue = gamepad.y;
                break;
            case LEFT_BUMPER:
                buttonValue = gamepad.left_bumper;
                break;
            case RIGHT_BUMPER:
                buttonValue = gamepad.right_bumper;
                break;
            case DPAD_UP:
                buttonValue = gamepad.dpad_up;
                break;
            case DPAD_DOWN:
                buttonValue = gamepad.dpad_down;
                break;
            case DPAD_LEFT:
                buttonValue = gamepad.dpad_left;
                break;
            case DPAD_RIGHT:
                buttonValue = gamepad.dpad_right;
                break;
            case BACK:
                buttonValue = gamepad.back;
                break;
            case START:
                buttonValue = gamepad.start;
                break;
            case LEFT_STICK_BUTTON:
                buttonValue = gamepad.left_stick_button;
                break;
            case RIGHT_STICK_BUTTON:
                buttonValue = gamepad.right_stick_button;
                break;
            default:
                buttonValue = false;
                break;
        }
        return buttonValue;
    }

    public boolean isTrigger(float trigger) {return trigger >= 0.5;}
    public boolean isTrigger(float trigger, double tolerance) {
        return trigger >= tolerance;
    }

    public float left_stick_x() {return this.gamepad.left_stick_x;}
    public float left_stick_y() {return this.gamepad.left_stick_y;}
    public float right_stick_x() {return this.gamepad.right_stick_x;}
    public float right_stick_y() {return this.gamepad.right_stick_y;}
}
