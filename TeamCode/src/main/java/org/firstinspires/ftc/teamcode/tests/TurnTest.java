package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;

//@Disabled
@TeleOp(name="Turn Test", group="TEST")
public class TurnTest extends Dynawheels {
    ImuSensor imu;
    TurnDrivetrainTo action;
    Coords FORWARD = new Coords(360, Coords.Unit.RoadRunner),
            LEFT = new Coords(90, Coords.Unit.RoadRunner),
            BACKWARD = new Coords(180, Coords.Unit.RoadRunner),
            RIGHT = new Coords(270, Coords.Unit.RoadRunner),
            B1_A1 = new Coords(23.4, Coords.Unit.Imu),
            B1_A2 = new Coords(45, Coords.Unit.Imu),
            B1_A3 = new Coords(31, Coords.Unit.Imu),
            B1_A4 = new Coords(0, Coords.Unit.Imu),
            B1_A5 = new Coords(15, Coords.Unit.Imu);

    @Override
    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        imu = new ImuSensor(hardwareMap, telemetry);
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            if (gamepad1.left_stick_y != 0 ||
                    gamepad1.left_stick_x != 0 ||
                    gamepad1.right_stick_x != 0) {

                drivetrain.fieldDrive(
                        -gamepad1.left_stick_y,
                        gamepad1.left_stick_x,
                        gamepad1.right_stick_x,
                        imu.getYaw()
                );
            }
            else {
                drivetrain.stop();
            }

            handleAction();
            if (action == null || action.run()) {
                action = null;
            }
        }
    }

    private void handleAction() {
        if (gamepad1.x) action = new TurnDrivetrainTo(drivetrain, imu, LEFT);
        else if (gamepad1.a) action = new TurnDrivetrainTo(drivetrain, imu, BACKWARD);
        else if (gamepad1.b) action = new TurnDrivetrainTo(drivetrain, imu, RIGHT);
        else if (gamepad1.y) action = new TurnDrivetrainTo(drivetrain, imu, FORWARD);
        else if (gamepad1.dpad_left) action = new TurnDrivetrainTo(drivetrain, imu, B1_A1);
        else if (gamepad1.dpad_up) action = new TurnDrivetrainTo(drivetrain, imu, B1_A2);
        else if (gamepad1.dpad_right) action = new TurnDrivetrainTo(drivetrain, imu, B1_A3);
        else if (gamepad1.dpad_down) action = new TurnDrivetrainTo(drivetrain, imu, B1_A4);
    }

    @Override
    public void cleanup() {
        drivetrain.stop();
    }
}
