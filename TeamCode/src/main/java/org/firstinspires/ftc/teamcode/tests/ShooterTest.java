package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;

@Disabled
@TeleOp(name="Shooter Test", group="TEST")
public class ShooterTest extends Dynawheels {
    @Override
    public void config() {
        shooter = new Shooter(hardwareMap, telemetry);
        lift = new Lift(hardwareMap, telemetry);
        carousel = new Carousel(hardwareMap, telemetry);
        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            if (gamepad1.right_trigger != 0) shooter.start();
            else if (gamepad1.right_bumper) shooter.stop();

            if (gamepad1.a) shooter.decreasePwr();
            else if (gamepad1.y) shooter.increasePwr();

            if (gamepad1.x) lift.up();
            else if (gamepad1.b) lift.down();

            if (gamepad1.dpad_down) carousel.spin();
            sleep(50);
        }
    }

    @Override
    public void cleanup() {
        shooter.stop();
    }
}
