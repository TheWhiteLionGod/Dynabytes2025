package org.firstinspires.ftc.teamcode;

// Teleop Specific Methods
public interface Controller extends Robot {
    // Field Drive Movements
    public void fieldMoveDriveTrain(double pwrx, double pwry);

    // Gear System
    public void changeGearMode(int val);
}
