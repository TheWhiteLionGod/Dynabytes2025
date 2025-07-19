// Imports
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

// Main Class
public class Dynawheels extends LinearOpMode {
    // Declaring Hardware Variables
    public DcMotor BL, FL,FR, BR;
    public IMU imu;

    // Gear System
    public double gear_mode = 3.0;
    boolean on_gear_switch_cooldown = false;
    double gear_switch_time = 0.0;
    double MAX_GEAR = 3.0;

    // YAW
    public double yaw_angle = 0.0;
/*
    public double YAWFWD = 0;
    public double YAWLEFT = 90;
    public double YAWRIGHT = -90;
    public double YAWBWD = 180;
*/

    @Override
    public void runOpMode() {

    }

    // Functions
    public void config() {
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

    public void changeGearMode(int change_val) {
        if (on_gear_switch_cooldown) {
            if (getRuntime() - gear_switch_time >= 0.25) {
                on_gear_switch_cooldown = false;
            }
        } else {
            gear_mode = Math.min(Math.max(gear_mode + change_val, 1), MAX_GEAR);
            on_gear_switch_cooldown = true;
            gear_switch_time = getRuntime();

            telemetry.addData("Current Gear Mode", gear_mode);
            telemetry.update();
        }
    }

    public void Move(double pwrx, double pwry) {
        double gear_pwr = gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*(-pwrx-pwry));
        FR.setPower(gear_pwr*(-pwrx-pwry));

        BR.setPower(gear_pwr*(pwrx-pwry));
        FL.setPower(gear_pwr*(pwrx-pwry));
    }

    public void fieldDriveMove(double pwr_x, double pwr_y) {
        double pi = 3.1415926;

        /* Adjust Joystick X/Y inputs by navX MXP yaw angle */
        double yaw_radians = yaw_angle * pi/180;
        double temp = pwr_y * Math.cos(yaw_radians) + pwr_x * Math.sin(yaw_radians);
        pwr_x = -pwr_y * Math.sin(yaw_radians) + pwr_x * Math.cos(yaw_radians);
        pwr_y = temp;

        /* At this point, Joystick X/Y (strafe/forward) vectors have been */
        /* rotated by the gyro angle, and can be sent to drive system */
        Move(pwr_x, pwr_y);
    }

    public void Turn(double pwr) {
        double gear_pwr = gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*pwr);
        FR.setPower(gear_pwr*-pwr);

        FL.setPower(0);
        BR.setPower(0);
    }

    public void Reset() {
        BL.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
    }
}
