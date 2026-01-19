package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;

@TeleOp(name="Shooter Test", group="TEST")
public class ShooterTest extends Dynawheels {
    @Override
    public void config() {
        shooter = new Shooter(hardwareMap, telemetry);
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            if (gamepad1.right_trigger != 0) shooter.start();
            else if (gamepad1.right_bumper) shooter.stop();

            if (gamepad1.a) shooter.decreasePwr();
            else if (gamepad1.y) shooter.increasePwr();

            telemetry.update();
            sleep(50);
        }
    }

    @Override
    public void cleanup() {
        shooter.stop();
    }
}
