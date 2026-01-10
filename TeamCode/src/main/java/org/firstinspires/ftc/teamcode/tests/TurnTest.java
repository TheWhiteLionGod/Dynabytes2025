package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;

@Disabled
//@TeleOp(name="Turn Test", group="TEST")
public class TurnTest extends Dynawheels {
    ImuSensor imu;
    @Override
    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        imu = new ImuSensor(hardwareMap, telemetry);
    }

    @Override
    public void run() {
        if (gamepad1.left_stick_y != 0 ||
                gamepad1.left_stick_x != 0 ||
                gamepad1.right_stick_x != 0) {

            drivetrain.fieldDrive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    imu.getYawRadians()
            );
        }
        else {
            drivetrain.stop();
        }

        TurnDrivetrainTo action;
        if (gamepad1.x) action = new TurnDrivetrainTo(90, drivetrain, imu);
        else if (gamepad1.a) action = new TurnDrivetrainTo(180, drivetrain, imu);
        else if (gamepad1.b) action = new TurnDrivetrainTo(270, drivetrain, imu);
        else if (gamepad1.y) action = new TurnDrivetrainTo(360, drivetrain, imu);
    }
}
