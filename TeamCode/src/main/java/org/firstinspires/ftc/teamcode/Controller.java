package org.firstinspires.ftc.teamcode;

// Teleop Specific Methods
public interface Controller extends Robot {
    // Field Drive Movements
    void fieldMoveDriveTrain(double pwrx, double pwry);

    // Gear System
    void changeGearMode(int val);
}
