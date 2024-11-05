package org.firstinspires.ftc.teamcode.wagner;

public class Toggleable {
    public boolean state;
    private boolean last;
    public boolean update(boolean curr) {
        if (curr) {
            if (!last)  state = !state;
            last = true;
        } else {
            last = false;
        }

        return state;
    }
}
