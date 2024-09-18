package org.firstinspires.ftc.teamcode.wagner;

import com.qualcomm.robotcore.hardware.Gamepad;

public class NGGamepad {
    public Gamepad gamepad;
    public NGGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public boolean isTrigger(float trigger) {return trigger >= 0.5;}
    public boolean isTrigger(float trigger, double tolerance) {
        return trigger >= tolerance;
    }

    public float left_stick_x() {return this.gamepad.left_stick_x;}
    public float left_stick_y() {return -this.gamepad.left_stick_y;}
    public float right_stick_x() {return this.gamepad.right_stick_x;}
    public float right_stick_y() {return -this.gamepad.right_stick_y;}
}
