package org.firstinspires.ftc.teamcode.trilobytes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Config
public class Scheduler {
    public static boolean enableDebugLogging = true;
    private final Queue<Trilobyte> trilobytes;
    private final Telemetry telemetry;

    public Scheduler(Telemetry t) {
        trilobytes = new LinkedList<>();
        this.telemetry = t;
    }

    public void queue(Trilobyte command) {
        trilobytes.add(command);
        TrilobyteState endState = command.runOnce();
        command.setState(endState);
    }

    public void update() {
        for(int i = 0; i < trilobytes.size(); i++) {
            Trilobyte cmd = trilobytes.poll();

            if (cmd != null) {
                TrilobyteState endState = cmd.runPeriodic();
                cmd.setState(endState);

                if(enableDebugLogging) telemetry.addData(cmd.getName() + "|ES", endState.name());

                if(!cmd.getPostRunRemoval()) trilobytes.add(cmd);
            }
        }
    }
}
