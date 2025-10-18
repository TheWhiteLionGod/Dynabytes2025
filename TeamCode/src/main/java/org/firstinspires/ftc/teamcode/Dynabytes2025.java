package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.path.EmptyPathSegmentException;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "Controller", group = "FTC2025")
public class Dynabytes2025 extends Robot {
    DcMotor BL, FL, FR, BR;
    DcMotor OM, IM;
    Servo Carousel, Lift;
    ColorSensor colorSensor;
    Thread SpinCarousel, RunLauncher;
    RunLauncherThread LauncherRunnable;
    double yaw_angle;
    double gear_mode = 3.0;
    boolean on_gear_switch_cooldown = false;
    double gear_switch_time;
    final double MAX_GEAR = 3.0;
    final int GREEN_BALL = 0;
    final int PURPLE_BALL = 1;

    @Override
    public void configure() {
        BL = hardwareMap.dcMotor.get("back_left_motor");
        FL = hardwareMap.dcMotor.get("front_left_motor");
        FR = hardwareMap.dcMotor.get("front_right_motor");
        BR = hardwareMap.dcMotor.get("back_right_motor");

        BL.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);

        OM = hardwareMap.dcMotor.get("OM");
        IM = hardwareMap.dcMotor.get("IM");

        Carousel = hardwareMap.servo.get("Carousel");
        Lift = hardwareMap.servo.get("Lift");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        drive = new SampleMecanumDrive(hardwareMap);
    }

    @Override
    public void run() {
        while (canRun()) {
            // START
            // Update Position in Road Runner
            drive.update();
            yaw_angle = Math.toDegrees(drive.getPoseEstimate().getHeading());
    
            // Switching Gears
            if (gamepad1.right_bumper) {
                changeGearMode(1);
            }
            else if (gamepad1.left_bumper) {
                changeGearMode(-1);
            }

            // Field Drive Movement
            if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                fieldDriveMove(gamepad1.left_stick_x, gamepad1.left_stick_y);
            }
            else {
                Reset();
            }

            // Turning
            if (gamepad1.right_stick_x != 0) {
                Turn(gamepad1.right_stick_x);
            }

            // Going to "Base"
            if (gamepad1.dpad_left) {
                goToRedBase();
            }
            else if (gamepad1.dpad_right) {
                goToBlueBase();
            }

            // Roller Controls
            if (gamepad1.left_trigger != 0) {
                startIntake();
            }
            else {
                stopIntake();
            }

            // Launcher Controls
            if ((gamepad1.right_trigger != 0) &&
                    (SpinCarousel == null || !RunLauncher.isAlive())) {
                if (RunLauncher == null || !RunLauncher.isAlive()) {
                    LauncherRunnable = new RunLauncherThread(OM, Lift);
                    RunLauncher = new Thread(LauncherRunnable);
                    RunLauncher.start();
                }
                else if (LauncherRunnable.canInterrupt) {
                    RunLauncher.interrupt();
                }
            }

            // Carousel Controls
            if ((SpinCarousel == null || !SpinCarousel.isAlive())
                    && (RunLauncher == null || !RunLauncher.isAlive())) {
                Runnable run = null;
                if (gamepad1.x) {
                    run = new SpinCarouselThread(colorSensor, Carousel, GREEN_BALL);
                }
                else if (gamepad1.a) {
                    run = new SpinCarouselThread(colorSensor, Carousel, PURPLE_BALL);
                }

                if (run != null) {
                    SpinCarousel = new Thread(run);
                    SpinCarousel.start();
                }
            }
            //ENDS
        }
        Reset();
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

    public void Move(double pwrx, double pwry) {
        double gear_pwr = gear_mode / MAX_GEAR;
        BL.setPower(gear_pwr*(-pwrx-pwry));
        FR.setPower(gear_pwr*(-pwrx-pwry));

        FL.setPower(gear_pwr*(pwrx-pwry));
        BR.setPower(gear_pwr*(pwrx-pwry));
    }

    public void fieldDriveMove(double pwr_x, double pwr_y) {
        /* Adjust Joystick X/Y inputs by navX MXP yaw angle */
        double yaw_radians = Math.toRadians(yaw_angle);
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

    public void startIntake() {
        IM.setPower(0.5);
    }

    public void stopIntake() {
        IM.setPower(0);
    }
}