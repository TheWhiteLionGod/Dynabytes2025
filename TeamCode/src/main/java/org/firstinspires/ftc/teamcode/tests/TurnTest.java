package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
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

        if (gamepad1.x) drivetrain.turnTo(imu, 90);
        else if (gamepad1.a) drivetrain.turnTo(imu, 180);
        else if (gamepad1.b) drivetrain.turnTo(imu, 270);
        else if (gamepad1.y) drivetrain.turnTo(imu, 0);
    }
}
