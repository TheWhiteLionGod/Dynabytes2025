package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;

//@Disabled
@TeleOp(name="Turn Test", group="TEST")
@Disabled
public class TurnTest extends Dynawheels {
    Action turnDrivetrainTo;
    static Coords FORWARD = new Coords(360, Coords.Unit.RoadRunner),
            LEFT = new Coords(90, Coords.Unit.RoadRunner),
            BACKWARD = new Coords(180, Coords.Unit.RoadRunner),
            RIGHT = new Coords(270, Coords.Unit.RoadRunner),
            B1_A1 = new Coords(17, Coords.Unit.Imu),
            B1_A2 = new Coords(45, Coords.Unit.Imu),
            B1_A3 = new Coords(25, Coords.Unit.Imu),
            B1_A4 = new Coords(0, Coords.Unit.Imu),
            R1_A1 = new Coords(-17, Coords.Unit.Imu),
            R1_A2 = new Coords(-45, Coords.Unit.Imu),
            R1_A3 = new Coords(-25, Coords.Unit.Imu),
            R1_A4 = new Coords(0, Coords.Unit.Imu),
            B2_A1 = new Coords(60, Coords.Unit.Imu),
            B2_A2 = new Coords(83, Coords.Unit.Imu),
            B2_A3 = new Coords(70, Coords.Unit.Imu),
            B2_A4 = new Coords(45, Coords.Unit.Imu),
            R2_A1 = new Coords(-60, Coords.Unit.Imu),
            R2_A2 = new Coords(-83, Coords.Unit.Imu),
            R2_A3 = new Coords(-70, Coords.Unit.Imu),
            R2_A4 = new Coords(-45, Coords.Unit.Imu);

    enum Position {
        B1(B1_A1, B1_A2, B1_A3, B1_A4),
        R1(R1_A1, R1_A2, R1_A3, R1_A4),
        B2(B2_A1, B2_A2, B2_A3, B2_A4),
        R2(R2_A1, R2_A2, R2_A3, R2_A4);

        private final Coords[] angles;

        Position(Coords... angles) {
            this.angles = angles;
        }

        Coords getAngle(int index) {
            return angles[index];
        }

        Position next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

    Position position = Position.B1;

    @Override
    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        imu = new ImuSensor(hardwareMap, telemetry);
        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            handleAction();

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
            else if (turnDrivetrainTo == null || turnDrivetrainTo.run()) {
                turnDrivetrainTo = null;
                drivetrain.stop();
            }

            telemetry.addData("Position", position.toString());
            telemetry.addData("Yaw Angle", imu.getYaw().yaw);
            telemetry.update();
        }
    }

    private void handleAction() {
        if (gamepad1.x) turnTo(LEFT);
        else if (gamepad1.a) turnTo(BACKWARD);
        else if (gamepad1.b) turnTo(RIGHT);
        else if (gamepad1.y) turnTo(FORWARD);

        else if (gamepad1.right_bumper) position = position.next();

        else if (gamepad1.dpad_left) turnTo(position.getAngle(0));
        else if (gamepad1.dpad_up) turnTo(position.getAngle(1));
        else if (gamepad1.dpad_right) turnTo(position.getAngle(2));
        else if (gamepad1.dpad_down) turnTo(position.getAngle(3));
    }

    private void turnTo(Coords heading) {
        turnDrivetrainTo = new TurnDrivetrainTo(drivetrain, imu, heading);
    }

    @Override
    public void cleanup() {
        drivetrain.stop();
    }
}
