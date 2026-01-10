package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="PID Test", group="TEST")
public class PIDTest extends LinearOpMode {
    public DcMotorEx launcher;
    public double targetVel = 3000, targetTPS;
    public final double KP = 0.0005;

    @Override
    public void runOpMode() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {
            // ticks = 2380 | 6000rpm
            if (gamepad1.y) targetVel += 1;
            else if (gamepad1.a) targetVel -= 1;

            // Calculating Error from TPS
            targetTPS = 2500 * targetVel / 6000;
            double error = targetTPS - launcher.getVelocity();

            // Calculating New Power
            double calcPwr = launcher.getPower() + (error * KP);
            calcPwr = Range.clip(calcPwr, -1, 1);
            launcher.setPower(calcPwr);

            telemetry.addData("Current Velocity", launcher.getVelocity());
            telemetry.addData("Target Ticks",targetTPS);
            telemetry.addData("Target Speed ","%4.2f, %4.2f", targetVel, targetVel/6000);
            telemetry.addData("Error", error);
            telemetry.addData("Calculated Power", calcPwr);
            telemetry.update();
        }
    }
}