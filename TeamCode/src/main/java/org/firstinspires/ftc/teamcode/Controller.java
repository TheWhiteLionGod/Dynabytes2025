package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Controller extends Robot {
    @Override
    public void configure() {
        BL = hardwareMap.dcMotor.get("BL");
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");

        BL.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);

        OM = hardwareMap.dcMotor.get("OM");
        IM = hardwareMap.dcMotor.get("IM");

        Carousel = hardwareMap.servo.get("Carousel");
        Lift = hardwareMap.servo.get("Lift");
        colorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        drive = new SampleMecanumDrive(hardwareMap);
    }

    @Override
    public void run() {
        while (canRun()) {
            updateOdometry();

            // Switching Gears
            if (gamepad1.y) {
                changeGearMode(1);
            }
            else if (gamepad1.a) {
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
            if (gamepad1.left_stick_button) {
                goToBlueBase();
            }
            else if (gamepad1.right_stick_button) {
                goToRedBase();
            }

            // Roller Controls
            if (gamepad1.left_trigger != 0) {
                forwardIntake();
            }
            else if (gamepad1.left_bumper) {
                backwardIntake();
            }
            else {
                stopIntake();
            }

            // Launcher Controls
            if ((RunLauncher == null || !RunLauncher.isAlive())
                    && (SpinCarousel == null || !SpinCarousel.isAlive())) {
                if (gamepad1.right_trigger != 0) {
                    RunLauncher = new FunctionThread(this::startLauncher, this::stopLauncher);
                    RunLauncher.start();
                }
                else if (gamepad1.right_bumper && RunLauncher != null) {
                    RunLauncher.interrupt();
                }
            }

            // Carousel Controls
            if ((SpinCarousel == null || !SpinCarousel.isAlive())
                    && (RunLauncher == null || !RunLauncher.isAlive())) {

                SpinCarousel = null;

                if (gamepad1.x) {
                    SpinCarousel = new FunctionThread(this::findGreenBall, () -> {});
                }
                else if (gamepad1.b) {
                    SpinCarousel = new FunctionThread(this::findPurpleBall, () -> {});
                }
                else if (gamepad1.dpad_down) {
                    SpinCarousel = new FunctionThread(this::spinCarousel, () -> {});
                }
                else if (gamepad1.dpad_left) {
                    SpinCarousel = new FunctionThread(() -> Thread.sleep(500), () -> {});
                    spinCarousel(Constants.CAROUSEL_POS_1);
                }
                else if (gamepad1.dpad_up) {
                    SpinCarousel = new FunctionThread(() -> Thread.sleep(500), () -> {});
                    spinCarousel(Constants.CAROUSEL_POS_2);
                }
                else if (gamepad1.dpad_right) {
                    SpinCarousel = new FunctionThread(() -> Thread.sleep(500), () -> {});
                    spinCarousel(Constants.CAROUSEL_POS_3);
                }

                if (SpinCarousel != null ) {
                    SpinCarousel.start();
                }
            }
        }

        Reset();
        SpinCarousel.interrupt(); // Stopping Carousel Spinning
        RunLauncher.interrupt(); // Stopping Shooting Mechanism
    }
}
