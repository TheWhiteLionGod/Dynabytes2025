// Imports
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

// Main Class
public class Dynawheels extends LinearOpMode implements Controller {
    // Declaring Hardware Variables
    public DcMotor BL, FL, FR, BR;
    public IMU imu;

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

    // PI Constant
    final double PI = 3.1415926;

    @Override
    public void runOpMode() {

    }

    // Functions
    @Override
    public void configureRobot() {
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
    }

    @Override
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

    public void updateYaw() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        yaw_angle = orientation.getYaw(AngleUnit.DEGREES);
    }

    @Override
    public void moveDriveTrain(double pwrx, double pwry) {
        double gear_pwr = cur_gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*(-pwrx-pwry));
        FR.setPower(gear_pwr*(-pwrx-pwry));

        BR.setPower(gear_pwr*(pwrx-pwry));
        FL.setPower(gear_pwr*(pwrx-pwry));
    }

    @Override
    public void fieldMoveDriveTrain(double pwr_x, double pwr_y) {
        /* Adjust Joystick X/Y inputs by navX MXP yaw angle */
        double yaw_radians = yaw_angle * PI/180;
        double temp = pwr_y * Math.cos(yaw_radians) + pwr_x * Math.sin(yaw_radians);
        pwr_x = -pwr_y * Math.sin(yaw_radians) + pwr_x * Math.cos(yaw_radians);
        pwr_y = temp;

        /* At this point, Joystick X/Y (strafe/forward) vectors have been */
        /* rotated by the gyro angle, and can be sent to drive system */
        moveDriveTrain(pwr_x, pwr_y);
    }

    @Override
    public void turnDriveTrain(double pwr) {
        double gear_pwr = cur_gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*pwr);
        FR.setPower(gear_pwr*-pwr);

        FL.setPower(gear_pwr*pwr);
        BR.setPower(gear_pwr*-pwr);
    }

    @Override
    public void resetDriveTrain() {
        BL.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
    }
}
