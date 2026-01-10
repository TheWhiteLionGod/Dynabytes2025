package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="PID TestVeer", group="TEST")
public class PIDTestVeet extends LinearOpMode {
    public DcMotorEx launcher;
    public double target_tics = 2300;
    public double target_speed = 3000;

    @Override
    public void runOpMode() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {
            // ticks = 2380 | 6000rpm
            if (gamepad1.y) {
                target_speed += 100;
                sleep(100);
            } else if (gamepad1.a) {
                target_speed -= 100;
                sleep(100);
            }


            target_tics= 2500*target_speed/6000;
            //launcher.setPower(1.0);
            set_target_speed(target_tics, .5);
            telemetry.addData("current speed", launcher.getVelocity());
            telemetry.addData("target tics",target_tics);
            telemetry.addData("Target Speed ","%4.2f, %4.2f", target_speed,target_speed/6000);
            telemetry.update();
            sleep(1000);


        }

    }

    public void set_target_speed(double target_speed, double curr_power) {
        double calc_power = 0;
        double curr_speed = launcher.getVelocity();
        double error = target_speed-curr_speed;
        double kp = 0.0005;
        double epoc=0;
        while (Math.abs(error) >= 50 && epoc<=3000){
            error = target_speed-curr_speed;
            calc_power = curr_power + (error * kp);

            calc_power = Range.clip(calc_power, -1, 1);
            launcher.setPower(calc_power);
            //sleep(2);
            curr_speed = launcher.getVelocity();
            telemetry.addData("Target tics", target_speed);
            telemetry.addData("Current Velocity", curr_speed);
            telemetry.addData("Error", error);
            telemetry.addData("calculated", calc_power);
            telemetry.addData("EPOC", epoc);
            telemetry.update();
            //sleep(1000);
            epoc++;

        }
        launcher.setPower(calc_power);
    }
}