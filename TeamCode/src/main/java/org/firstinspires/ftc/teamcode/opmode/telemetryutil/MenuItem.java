package org.firstinspires.ftc.teamcode.opmode.telemetryutil;

import androidx.annotation.NonNull;

public class MenuItem {
    private String title;
    private Runnable onSelected;
    private Runnable onClick;
    private Runnable periodicUpdate;
    public MenuItem(String title) {
        this.title = title;
        setSelected(()->{});
        setPeriodicUpdate(()->{});
        setOnClick(()->{});
    }

    public void setPeriodicUpdate(Runnable i) {this.periodicUpdate = i;}
    public void setSelected(Runnable i) {this.onSelected = i;}
    public void setOnClick(Runnable i) {this.onClick = i;}

    public void runPeriodic() {periodicUpdate.run();}
    public void runOnSelected() {onSelected.run();}
    public void runOnClick() {onClick.run();}

    public void setTitle(String m) {this.title = m;}

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
