package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;

public class TurnDrivetrainTo extends Action {
    double targetYaw;
    public TurnDrivetrainTo(double targetYaw, FieldDrive drivetrain, ImuSensor imu) {
        super(drivetrain, imu);

        targetYaw = (targetYaw <= 180) ? targetYaw : -(360 - targetYaw);
        this.targetYaw = targetYaw;
    }

    @Override
    public void run() {
        FieldDrive drivetrain = (FieldDrive) subsystems.get(0);
        ImuSensor imu = (ImuSensor) subsystems.get(1);

        double err, turnPwr;

        do {
            err = imu.getYawDegrees() - targetYaw;
            turnPwr = Math.copySign(Math.min(Math.abs(err * Constants.DRIVETRAIN_TURN_KP), 1), err);

            drivetrain.robotDrive(0, 0, turnPwr);
        } while (Math.abs(err) > 5);

        drivetrain.stop();
    }
}
