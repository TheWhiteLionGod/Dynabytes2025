package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;

public class TurnDrivetrainTo implements Action {
    FieldDrive drivetrain;
    ImuSensor imu;
    Coords targetYaw;
    public TurnDrivetrainTo(FieldDrive drivetrain, ImuSensor imu, Coords targetYaw) {
        this.drivetrain = drivetrain;
        this.imu = imu;
        this.targetYaw = targetYaw.toImu();
    }

    public boolean run() {
        double err = imu.getYaw().yaw - targetYaw.yaw;
        if (Math.abs(err) > 180) {
            err %= 180;
            err *= -1;
        }

        double turnPwr = Math.copySign(0.5, -err);
        drivetrain.robotDrive(0, 0, turnPwr);
        return Math.abs(err) <= 3;
    }
}
