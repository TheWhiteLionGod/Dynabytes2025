package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Action {
    ArrayList<Subsystem> subsystems;

    public Action(Subsystem... subsystems) {
        this.subsystems = new ArrayList<>();
        this.subsystems.addAll(Arrays.asList(subsystems));
    }

    public abstract void run();
}
