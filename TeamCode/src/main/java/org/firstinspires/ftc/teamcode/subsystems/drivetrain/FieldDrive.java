package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public class FieldDrive implements Subsystem {
    private final DcMotorEx BL, FL, FR, BR;
    private final Telemetry telemetry;

    public FieldDrive(HardwareMap hardwareMap, Telemetry telemetry) {
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        BR = hardwareMap.get(DcMotorEx.class, "BR");

        BL.setDirection(DcMotorEx.Direction.REVERSE);
        FL.setDirection(DcMotorEx.Direction.REVERSE);

        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.telemetry = telemetry;
        telemetry.addData("Drivetrain", "Initialized");
    }

    // Auto Commands
    public void forward() {
        robotDrive(0.7, 0, 0);
    }

    public void backward() {
        robotDrive(-0.7, 0, 0);
    }

    public void left() {
        robotDrive(0, -0.7, 0);
    }

    public void right() {
        robotDrive(0, 0.7, 0);
    }

    // Field Drive Movement
    public void fieldDrive(double forward, double strafe, double turn, Coords coords) {
        double curYaw = Math.toRadians(-coords.toRoadRunner().yaw);

        double temp = forward * Math.cos(curYaw) + strafe * Math.sin(curYaw);
        strafe = -forward * Math.sin(curYaw) + strafe * Math.cos(curYaw);
        forward = temp;

        robotDrive(forward, strafe, turn);
    }

    // Regular Movement
    public void robotDrive(double forward, double strafe, double turn) {
        double frontLeft = forward + strafe + turn;
        double backLeft = forward - strafe + turn;
        double frontRight = forward - strafe - turn;
        double backRight = forward + strafe - turn;

        double max = Math.max(
                Math.max(Math.abs(frontLeft), Math.abs(backLeft)),
                Math.max(Math.abs(frontRight), Math.abs(backRight))
        );

        if (max > 1.0) {
            frontLeft /= max;
            backLeft /= max;
            frontRight /= max;
            backRight /= max;
        }

        BL.setPower(backLeft);
        FL.setPower(frontLeft);
        FR.setPower(frontRight);
        BR.setPower(backRight);

        telemetry.addData("Drivetrain", "Moving");
    }

    public void stop() {
        BL.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        telemetry.addData("Drivetrain", "Stopped");
    }
}
