package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;
import org.firstinspires.ftc.teamcode.actions.UpdateHeadLight;

@Disabled
//@TeleOp(name="Two Player Controller", group="FTC2026")
public class OnePlayerCtrl extends Dynawheels {
    Action findColorBall,
            updateHeadLight = new UpdateHeadLight(headLight, colorSensor, lift);

    @Override
    public void run() {
        while (opModeIsActive()) {
            handleDrivetrain();
            handleIntake();

            handleCarousel();
            handleOuttake();

            handleLights();
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
        if (gamepad1.left_trigger != 0) roller.forward();
        else if (gamepad1.leftBumperWasPressed()) roller.reverse();
        else roller.stop();
    }

    private void handleCarousel() {
        if (gamepad1.dpadDownWasPressed()) carousel.spin();
        else if (gamepad1.xWasPressed()) findColorBall = new FindColorBall(carousel, colorSensor, FindColorBall.Color.GREEN);
        else if (gamepad1.bWasPressed()) findColorBall = new FindColorBall(carousel, colorSensor, FindColorBall.Color.GREEN);

        if (findColorBall == null || findColorBall.run())
            findColorBall = null;
    }

    private void handleOuttake() {
        if (gamepad1.a) lift.down();
        else if (gamepad1.y) lift.up();

        if (gamepad1.right_trigger != 0) {
            shooter.setPwr(hyperbola(gamepad1.right_trigger));
            shooter.start();
        }
        else if (gamepad1.rightBumperWasPressed()) shooter.stop();
    }

    private void handleLights() {
        updateHeadLight.run();
    }

    private double hyperbola(double x) {
        return Math.max((4*x) / ((3*x) + 1), 0.6);
    }
}
