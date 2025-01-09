package org.firstinspires.ftc.teamcode;

public class Toggleable {
    public boolean state;
    boolean last;
    public boolean update(boolean current) {
        if(current) {
            if(!last) state = !state;
            last = true;
        } else {
            last = false;
        }
        return state;
    }
}
