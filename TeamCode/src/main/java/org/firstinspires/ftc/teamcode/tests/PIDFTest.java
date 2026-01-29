/*
WATCH THIS VIDEO BEFORE TUNING: https://www.youtube.com/watch?v=aPNCpZzCTKg
*/
package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Dynawheels;

@Disabled
@TeleOp(name="PIDF Test", group="TEST")
public class PIDFTest extends Dynawheels {
    DcMotorEx shooter;

    double P = 1.0;
    double F = 12.051;

    PIDFCoefficients PIDF = new PIDFCoefficients(P, 0, 0, F);

    double highTargetVel = 2500;
    double lowTargetVel = 0;
    double curTargetVel = lowTargetVel;

    double[] stepSize = new double[]{100, 10, 1, 0.1, 0.01, 0.001};
    int stepSizePtr = 0;

    @Override
    public void config() {
        shooter = hardwareMap.get(DcMotorEx.class, "Launcher");
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);

        telemetry.addData("Shooter", "Initialized");
        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            if (gamepad1.aWasPressed()) curTargetVel = lowTargetVel;
            else if (gamepad1.yWasPressed()) curTargetVel = highTargetVel;
            shooter.setVelocity(curTargetVel);

            if (gamepad1.dpad_up) P += stepSize[stepSizePtr];
            else if (gamepad1.dpad_down) P -= stepSize[stepSizePtr];

            if (gamepad1.dpad_left) F += stepSize[stepSizePtr];
            else if (gamepad1.dpad_right) F -= stepSize[stepSizePtr];

            if (gamepad1.xWasPressed()) stepSizePtr = (stepSizePtr + 1) % stepSize.length;
            else if (gamepad1.bWasPressed()) stepSizePtr = (stepSizePtr - 1) % stepSize.length;

            PIDF = new PIDFCoefficients(P, 0, 0, F);
            shooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);

            telemetry.addData("P", P);
            telemetry.addData("F", F);
            telemetry.addData("Current Velocity", shooter.getVelocity());
            telemetry.addData("Target Velocity", curTargetVel);
            telemetry.addData("Step Size", stepSize[stepSizePtr]);
            telemetry.update();
            sleep(10);
        }
    }

    @Override
    public void cleanup() {
        shooter.setPower(0);
    }
}
