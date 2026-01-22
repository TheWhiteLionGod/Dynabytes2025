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

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Controller-2Player", group="Linear OpMode")
//@Disabled
public class Controller2 extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo LLift = null;
    private Servo RLift = null;
    private Servo carousel = null;
    private DcMotor FLDrive = null;
    private DcMotor FRDrive = null;
    private DcMotor BLDrive = null;
    private DcMotor BRDrive = null;
    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    private Servo greenLight = null;
    private Servo purpleLight = null;
    private ColorSensor colorSensor = null;
    public double FirstLiftPos = 0;
    public double LastLiftPos = Constants.LIFT_UP_POS;
    public double pos = 0.11;

    public double current_servo_pos = 0.0;
    public double launcherSpeed = -0.9;

    public double pos1color = 0;
    public double pos2color = 0;
    public double pos3color = 0;
    float[] hsv = new float[3];
    float hue = hsv[0];


    boolean blink_flag = false;

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
        launcher = hardwareMap.get(DcMotorEx.class, "Launcher");
        intake = hardwareMap.get(DcMotor.class, "IntakeRoller");
        greenLight = hardwareMap.get(Servo.class, "GreenLight");
        purpleLight = hardwareMap.get(Servo.class, "PurpleLight");
        colorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        LLift.setDirection(Servo.Direction.REVERSE);

        //BLDrive.setDirection(DcMotor.Direction.REVERSE);
        // FLDrive.setDirection(DcMotor.Direction.REVERSE);
        BRDrive.setDirection(DcMotor.Direction.REVERSE);
        FRDrive.setDirection(DcMotor.Direction.REVERSE);

        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, Constants.shooterPIDF);


        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
        //frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        //backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        //frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        //backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        LLift.setPosition(FirstLiftPos);
        RLift.setPosition(FirstLiftPos);
        carousel.setPosition(.04);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // ########################################################################################
            // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
            // ########################################################################################
            // Most robots need the motors on one side to be reversed to drive forward.
            // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
            // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
            // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
            // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
            // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
            // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
            //frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
            //backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
            //frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
            //backRightDrive.setDirection(DcMotor.Direction.FORWARD);

            // Wait for the game to start (driver presses START)
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            waitForStart();
            runtime.reset();
            LLift.setPosition(FirstLiftPos);
            RLift.setPosition(FirstLiftPos);

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                /// Robot teleop logic code
                // ##### Drive movement ###############
                double max;

                // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
                double axial = -gamepad2.left_stick_y*0.85;  // Note: pushing stick forward gives negative value
                double lateral = -gamepad2.left_stick_x*0.85;
                double yaw = -gamepad2.right_stick_x*.5;

                // Combine the joystick requests for each axis-motion to determine each wheel's power.
                // Set up a variable for each drive wheel to save the power level for telemetry.
                double leftFrontPower = axial + lateral + yaw;
                double rightFrontPower = axial - lateral - yaw;
                double leftBackPower = axial - lateral + yaw;
                double rightBackPower = axial + lateral - yaw;

                // Normalize the values so no wheel power exceeds 100%
                // This ensures that the robot maintains the desired motion.
                max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
                max = Math.max(max, Math.abs(leftBackPower));
                max = Math.max(max, Math.abs(rightBackPower));

                if (max > 1.0) {
                    leftFrontPower /= max;
                    rightFrontPower /= max;
                    leftBackPower /= max;
                    rightBackPower /= max;
                }


                // Send calculated power to wheels
                FLDrive.setPower(leftFrontPower);
                FRDrive.setPower(rightFrontPower);
                BLDrive.setPower(leftBackPower);
                BRDrive.setPower(rightBackPower);

                if (pos == .04){
                    get_color();
                    if (Constants.GREEN_MIN <= hue && hue <= Constants.GREEN_MAX) {
                        pos1color = 1;
                    }
                    else if (Constants.PURPLE_MIN <= hue && hue <= Constants.PURPLE_MAX){
                        pos1color = 2;
                    }
                } else if (pos == .51) {
                    if (Constants.GREEN_MIN <= hue && hue <= Constants.GREEN_MAX) {
                        pos2color = 1;
                    }
                    else if(Constants.PURPLE_MIN <= hue && hue <= Constants.PURPLE_MAX) {
                        pos2color = 2;
                    }
                }
                else if (pos == .94){
                    if (Constants.GREEN_MIN <= hue && hue <= Constants.GREEN_MAX) {
                        pos3color = 1;
                    }
                    else if(Constants.PURPLE_MIN <= hue && hue <= Constants.PURPLE_MAX) {
                        pos3color = 2;
                    }
                }
                if (gamepad1.dpad_left){
                    if (pos1color == 2){
                        lift_down();
                        sleep(50);
                        carousel.setPosition(.04);
                    } else if (pos2color == 2) {
                        lift_down();
                        sleep(50);
                        carousel.setPosition(0.51);
                    }
                    else if (pos3color == 2){
                        lift_down();
                        sleep(50);
                        carousel.setPosition(0.94);
                    }
                }
                if (gamepad1.dpad_right){
                    if (pos1color == 1){
                        lift_down();
                        sleep(50);
                        carousel.setPosition(.04);
                    }else if (pos2color == 1) {
                        lift_down();
                        sleep(50);
                        carousel.setPosition(0.51);
                    }
                    else if (pos3color == 1){
                        lift_down();
                        sleep(50);
                        carousel.setPosition(0.94);
                    }
                }
                if (gamepad1.left_trigger > 0) {
                    roll_in();
                } else if (gamepad1.left_bumper) {
                    roll_out();
                } else if (gamepad1.right_stick_y > 0) {
                    roll_stop();

                }
                if (gamepad1.right_trigger > 0) {
                    launch();
                } else if (gamepad1.right_bumper) {
                    launch_stop();
                } else if (gamepad1.right_stick_x > 0) {
                    launch_stop();
                }
                if (gamepad1.dpad_down) {
                    lift_down();
                    if(pos == .04){
                        pos = 0.51;
                        carousel.setPosition(pos);
                    }
                    else if(pos == .51){
                        pos = 0.94;
                        carousel.setPosition(pos);
                    }
                    else{
                        pos = 0.04;
                        carousel.setPosition(pos);
                    }
                }

                if (gamepad1.x) {
                    lift_up();
                    if (pos == 0.04){
                        pos1color = 0;
                    }
                    else if (pos == 0.51){
                        pos2color = 0;
                    } else if (pos == .94) {
                        pos3color = 0;
                    }
                }
                if (gamepad1.b){
                    lift_down();
                }
                if (gamepad1.a) {
                    roll_stop();
                }

                if (gamepad2.a) {
                    decreaseLauncherSpeed();
                } else if (gamepad2.y) {
                    increaseLauncherSpeed();
                }

                get_color();
                if (Constants.GREEN_MIN <= hue && hue <= Constants.GREEN_MAX) {
                    greenLight.setPosition(0.5);
                    purpleLight.setPosition(0);
                } else if (Constants.PURPLE_MIN <= hue && hue <= Constants.PURPLE_MAX) {
                    greenLight.setPosition(0);
                    purpleLight.setPosition(0.5);
                }
                else {
                    greenLight.setPosition(0);
                    purpleLight.setPosition(0);
                }

                if (LLift.getPosition()==LastLiftPos){
                    // blinking

                    if(!blink_flag){
                        greenLight.setPosition(0.5);
                        purpleLight.setPosition(0.5);
                        blink_flag=true;
                    }
                    else{
                        greenLight.setPosition(0);
                        purpleLight.setPosition(0);
                        blink_flag=false;
                    }
                }


                telemetry.addData("Hue", hue);
                telemetry.addData("Shooter Power", launcherSpeed*-1);
                telemetry.addData("Back  left/Right", "%4.2f, %4.2f", current_servo_pos, LLift.getPosition());
                telemetry.update();


            }


        }
    }

    public void lift_up() {
        RLift.setPosition(LastLiftPos);
        sleep(100);
        LLift.setPosition(LastLiftPos);
    }

    public void lift_down() {
        LLift.setPosition(FirstLiftPos);
        sleep(150);
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

    public void turn_left() {
        FLDrive.setPower(0.5);
        FRDrive.setPower(0.5);
        BLDrive.setPower(-0.5);
        BRDrive.setPower(-0.5);
    }

    public void turn_right() {
        FLDrive.setPower(-0.5);
        FRDrive.setPower(-0.5);
        BLDrive.setPower(0.5);
        BRDrive.setPower(0.5);
    }

    public void strafe_left() {
        FLDrive.setPower(0.5);
        FRDrive.setPower(-0.5);
        BLDrive.setPower(0.5);
        BRDrive.setPower(-0.5);
    }

    public void strafe_right() {
        FLDrive.setPower(-0.5);
        FRDrive.setPower(0.5);
        BLDrive.setPower(-0.5);
        BRDrive.setPower(0.5);

    }
    public void rotate_pos1(){
        lift_down();
        sleep(50);
        carousel.setPosition(0.04);
    }
    public void rotate_pos2(){
        lift_down();
        sleep(50);
        carousel.setPosition(.51);
    }
    public void rotate_pos3(){
        lift_down();
        sleep(50);
        carousel.setPosition(.94);
    }
    public void roll_in(){
        intake.setPower(1);
    }
    public void roll_out(){
        intake.setPower(-1);
    }
    public void roll_stop(){
        intake.setPower(0);
    }
    public void launch(){
        launcher.setVelocity(launcherSpeed*Constants.shooterMaxVel);
    }
    public void launch_reverse(){
        launcher.setPower(0.7);
    }
    public void launch_stop(){
        launcher.setPower(0);
    }

    public void increaseLauncherSpeed() {
        launcherSpeed -= 0.005;
        launcherSpeed = Math.max(-1, launcherSpeed);
        launch();
    }
    public void decreaseLauncherSpeed() {
        launcherSpeed += 0.005;
        launcherSpeed = Math.min(0, launcherSpeed);
        launch();
    }
    public void robot_stop(){
        FLDrive.setPower(0);
        FRDrive.setPower(0);
        BLDrive.setPower(0);
        BRDrive.setPower(0);
    }
    public void get_color(){
        hsv = new float[3];
        Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);
        hue = hsv[0];
    }

}
