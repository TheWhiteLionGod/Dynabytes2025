/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;


@Deprecated
@Autonomous(name="Auto_Basket_Blue", group="Linear OpMode")
@Disabled
public class Auto_Basket_Blue extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo LLift = null;
    private Servo RLift = null;
    private Servo carousel = null;
    private DcMotor FLDrive = null;
    private DcMotor FRDrive = null;
    private DcMotor BLDrive = null;
    private DcMotor BRDrive = null;
    private DcMotor launcher = null;
    private DcMotor intake = null;
    public double FirstLiftPos = 0;
    public double LastLiftPos = 0.33;
    public double pos = 0.11;
    IMU imu;


    public double current_servo_pos = 0.0;

//    private DcMotor frontLeftDrive = null;
//    private DcMotor backLeftDrive = null;
//    private DcMotor frontRightDrive = null;
//    private DcMotor backRightDrive = null;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        FLDrive = hardwareMap.get(DcMotor.class, "FL");
        BLDrive = hardwareMap.get(DcMotor.class, "BL");
        FRDrive = hardwareMap.get(DcMotor.class, "FR");
        BRDrive = hardwareMap.get(DcMotor.class, "BR");
        LLift = hardwareMap.get(Servo.class, "LeftLIFT");
        RLift = hardwareMap.get(Servo.class, "RightLIFT");
        carousel = hardwareMap.get(Servo.class, "Carousel");
        launcher = hardwareMap.get(DcMotor.class, "Launcher");
        intake = hardwareMap.get(DcMotor.class, "IntakeRoller");
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        //imu initialize
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();

        FieldDrive drivetrain;
        RLift.setDirection(Servo.Direction.REVERSE);
        drivetrain = new FieldDrive(hardwareMap, telemetry);



        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        rotate_pos1();

        waitForStart();
        runtime.reset();
        LLift.setPosition(FirstLiftPos);
        RLift.setPosition(FirstLiftPos);
        // run until the end of the match (driver presses STOP)
        launch();
        sleep(1000);
        move_backward();
        sleep(800);
        robot_stop();
        sleep(400);
        lift_up();
        sleep(500);
        lift_down();
        sleep(500);
        rotate_pos2();
        sleep(1000);
        lift_up();
        sleep(500);
        lift_down();
        sleep(300);
        rotate_pos3();
        sleep(1000);
        lift_up();
        sleep(500);
        move_backward();
        sleep(200);
        robot_stop();
        turn_left();
        lift_down();
        sleep(300);
        turn_robot_with_gyro(-45);
        sleep(700);
        move_backward();
        sleep(500);
        robot_stop();
        turn_robot_with_gyro(-135);
        roll_in();
        sleep(200);
        move_backward();
        sleep(400);
        move_robot(0,0,0,0);
        rotate_pos2();
        sleep(400);
        move_backward();
        sleep(400);
        robot_stop();
        /*move_robot(0,0,0,0);
        rotate_pos3();
        sleep(400);
        rotate_pos1();
        robot_stop();
        roll_in();
        rotate_pos2();
        move_forward();
        sleep(800);
        robot_stop();
        turn_robot_with_gyro(-45);
        move_forward();
        sleep(1000);
        move_robot(0,0,0,0);
        turn_robot_with_gyro(0);
        strafe_right();
        sleep(600);
        move_robot(0,0,0,0);
        lift_up();
        */


    }

    public void lift_up() {
        RLift.setPosition(LastLiftPos);
    sleep(100);
        LLift.setPosition(LastLiftPos);
    }

    public void lift_down() {
        LLift.setPosition(FirstLiftPos);
        sleep(100);
        RLift.setPosition(FirstLiftPos);
    }

    public void move_forward() {
        FLDrive.setPower(0.5);
        FRDrive.setPower(0.5);
        BLDrive.setPower(0.5);
        BRDrive.setPower(0.5);
    }

    public void move_backward() {
        FLDrive.setPower(-0.5);
        FRDrive.setPower(-0.5);
        BLDrive.setPower(-0.5);
        BRDrive.setPower(-0.5);
    }

    public void strafe_left() {
        FLDrive.setPower(0.9);
        FRDrive.setPower(0.9);
        BLDrive.setPower(-0.9);
        BRDrive.setPower(-0.9);
    }

    public void strafe_right() {
        FLDrive.setPower(-1);
        FRDrive.setPower(-1);
        BLDrive.setPower(1);
        BRDrive.setPower(1);
    }

    public void turn_left() {
        FLDrive.setPower(0.5);
        FRDrive.setPower(-0.5);
        BLDrive.setPower(0.5);
        BRDrive.setPower(-0.5);
    }

    public void turn_right() {
        FLDrive.setPower(-0.5);
        FRDrive.setPower(0.5);
        BLDrive.setPower(-0.5);
        BRDrive.setPower(0.5);

    }
    public void rotate_pos1(){
        lift_down();
        carousel.setPosition(0.04);
    }
    public void rotate_pos2(){
        lift_down();
        carousel.setPosition(.51);
    }
    public void rotate_pos3(){
        lift_down();
        carousel.setPosition(.94);
    }
    public void roll_in(){
        intake.setPower(-1);
    }
    public void roll_out(){
        intake.setPower(1);
    }
    public void roll_stop(){
        intake.setPower(0);
    }
    public void launch(){
        launcher.setPower(-.7);
    }
    public void launch_reverse(){
        launcher.setPower(0.8);
    }
    public void launch_stop(){
        launcher.setPower(0);
    }
    public void robot_stop(){
        FLDrive.setPower(0);
        FRDrive.setPower(0);
        BLDrive.setPower(0);
        BRDrive.setPower(0);
    }
    public void move_robot(double LF, double RF, double LB, double RB){
        FLDrive.setPower(LF);
        BLDrive.setPower(LB);
        FRDrive.setPower(RF);
        BRDrive.setPower(RB);
    }
    public double getAngle(){
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        AngularVelocity angularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);
        double current_angle = orientation.getYaw(AngleUnit.DEGREES);

        if(current_angle>=180){
            current_angle=current_angle-360;
        }else if(current_angle<=-180){
            current_angle=current_angle+360;
        }
        return current_angle;
    }

    public void turn_robot_with_gyro(double target_angle){
        double error =target_angle - getAngle();
        double kp = 0.035;
        while(Math.abs(error)>2){
            double motor_power = (kp*error);
            motor_power = Range.clip(motor_power, -0.6, 0.6);
            move_robot(-motor_power,motor_power,-motor_power,motor_power);
            error = target_angle-getAngle();
        }
        robot_stop();
    }
    public void turn_to_angle(double angle){
        double ang = angle;
        if(getAngle() >= angle-4 && getAngle() <= angle+4){
            robot_stop();
        } else if (getAngle()<ang) {
            turn_robot_with_gyro(3);
        } else if (getAngle()>ang) {
            turn_robot_with_gyro(-3);
        }
    }
}

