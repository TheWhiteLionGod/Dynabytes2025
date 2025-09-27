// Imports
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.path.EmptyPathSegmentException;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

// Main Class
public abstract class Dynawheels extends Robot {
    // Declaring Hardware Variables
    public DcMotor BL, FL, FR, BR;
    public IMU imu;
    SampleMecanumDrive drive;

    // Gear System
    double cur_gear_mode = 3.0;
    double gear_switch_time = 0.0;
    final double MAX_GEAR = 3.0;

    // YAW
    double yaw_angle = 0.0;

/*
    public double YAWFWD = 0;
    public double YAWLEFT = 90;
    public double YAWRIGHT = -90;
    public double YAWBWD = 180;
*/

    // Functions
    @Override
    public void configure() {
        // Mapping Wheels
        BL = hardwareMap.get(DcMotor.class, "BL");
        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BR = hardwareMap.get(DcMotor.class, "BR");

        //Reversing Motors
        BL.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);

        //Yaw Angle Config
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        // Initializing IMU
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();

        // Configuring Robot Position During Init
        Pose2d start_pos = null;
        while (!isStarted()) {
            if (gamepad1.dpad_left) {
                System.out.println("BLUE TEAM + Away from Obelisk");
                start_pos = TrajectoryStorage.blue_down_pos;
            } else if (gamepad1.dpad_right) {
                System.out.println("RED TEAM + Away from Obelisk");
                start_pos = TrajectoryStorage.red_down_pos;
            } else if (gamepad1.dpad_up) {
                System.out.println("RED TEAM + In Launching Zone");
                start_pos = TrajectoryStorage.red_up_pos;

            } else if (gamepad1.dpad_down) {
                System.out.println("BLUE TEAM + In Launching Zone");
                start_pos = TrajectoryStorage.blue_up_pos;
            }

        }

        if (start_pos == null) {
            System.out.println("ROBOT NOT CONFIGURED");
            return;
        }

        drive.setPoseEstimate(start_pos);
    }

    public void changeGearMode(int change_val) {
        // Gear Switch Must Be On Cooldown
        if (getRuntime() - gear_switch_time <= 0.1) {
            return;
        }
        // Ensuring Gear Mode in Bounds
        cur_gear_mode = Math.min(Math.max(cur_gear_mode + change_val, 1), MAX_GEAR);
        gear_switch_time = getRuntime();

        // Telemetry
        telemetry.addData("Current Gear Mode", cur_gear_mode);
        telemetry.update();
    }

    // These Functions Will Take You to Your "Base"
    public void goToRedBase() {
        try {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(-3.25*12, 2.75*12, Math.toRadians(0)), 0)
                            .build()
            );
        }
        catch (EmptyPathSegmentException e) {
            System.out.println("Empty Path Segment Exception has Occurred\nThis means that the robot is already at the defined position");
        }
    }

    public void goToBlueBase() {
        try {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineToLinearHeading(new Pose2d(-3.25*12, -2.75*12, Math.toRadians(0)), 0)
                            .build()
            );
        }
        catch (EmptyPathSegmentException e) {
            System.out.println("Empty Path Segment Exception has Occurred\nThis means that the robot is already at the defined position");
        }
    }

    public void updateYaw() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        yaw_angle = orientation.getYaw(AngleUnit.DEGREES);
    }

    public void moveDriveTrain(double pwrx, double pwry) {
        double gear_pwr = cur_gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*(-pwrx-pwry));
        FR.setPower(gear_pwr*(-pwrx-pwry));

        BR.setPower(gear_pwr*(pwrx-pwry));
        FL.setPower(gear_pwr*(pwrx-pwry));
    }

    public void fieldMoveDriveTrain(double pwr_x, double pwr_y) {
        /* Adjust Joystick X/Y inputs by navX MXP yaw angle */
        double yaw_radians = Math.toRadians(yaw_angle);
        double temp = pwr_y * Math.cos(yaw_radians) + pwr_x * Math.sin(yaw_radians);
        pwr_x = -pwr_y * Math.sin(yaw_radians) + pwr_x * Math.cos(yaw_radians);
        pwr_y = temp;

        /* At this point, Joystick X/Y (strafe/forward) vectors have been */
        /* rotated by the gyro angle, and can be sent to drive system */
        moveDriveTrain(pwr_x, pwr_y);
    }

    public void turnDriveTrain(double pwr) {
        double gear_pwr = cur_gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*pwr);
        FR.setPower(gear_pwr*-pwr);

        FL.setPower(gear_pwr*pwr);
        BR.setPower(gear_pwr*-pwr);
    }

    public void resetDriveTrain() {
        BL.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
    }
}
