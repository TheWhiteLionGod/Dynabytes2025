package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;

@Disabled
//@TeleOp(name="Two Player Controller", group="FTC2026")
public class TwoPlayerCtrl extends Dynawheels {
    Action findColorBall;

    @Override
    public void run() {
        while (opModeIsActive()) {
            handleDrivetrain();
            handleIntake();

            handleCarousel();
            handleOuttake();

            telemetry.update();
            sleep(50);
        }
    }

    private void handleDrivetrain() {
        if (gamepad1.left_stick_y != 0||
            gamepad1.left_stick_x != 0 ||
            gamepad1.right_stick_x != 0) {

            drivetrain.fieldDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, imu.getYaw());
        }
        else {
            drivetrain.stop();
        }
    }

    private void handleIntake() {
        if (gamepad2.left_trigger != 0) roller.forward();
        else if (gamepad2.left_bumper) roller.reverse();
        else roller.stop();
    }

    private void handleCarousel() {
        if (gamepad2.dpad_down) carousel.spin();
        else if (gamepad2.x) findColorBall = new FindColorBall(carousel, colorSensor, FindColorBall.Color.GREEN);
        else if (gamepad2.b) findColorBall = new FindColorBall(carousel, colorSensor, FindColorBall.Color.GREEN);

        if (findColorBall == null || findColorBall.run())
            findColorBall = null;
    }

    private void handleOuttake() {
        if (gamepad2.a) lift.down();
        else if (gamepad2.y) lift.up();

        if (gamepad2.right_trigger != 0) {
            shooter.setPwr(hyperbola(gamepad2.right_trigger));
            shooter.start();
        }
        else if (gamepad2.right_bumper) shooter.stop();
    }

    private double hyperbola(double x) {
        return Math.max((4*x) / ((3*x) + 1), 0.6);
    }
}
