package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;
import org.firstinspires.ftc.teamcode.actions.UpdateHeadLight;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;

//@Disabled
@TeleOp(name="One Player Controller", group="FTC2026")
public class OnePlayerCtrl extends Dynawheels {
    Action findColorBall, updateHeadLight;

    @Override
    public void config() {
        super.config();
        updateHeadLight = new UpdateHeadLight(headLight, colorSensor, lift);
    }

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
        else if (gamepad1.left_bumper) roller.reverse();
        else roller.stop();
    }

    private void handleCarousel() {
        if (gamepad1.dpad_down) carousel.spin();
        else if (gamepad1.x) findColorBall = new FindColorBall(carousel, colorSensor, BallColor.GREEN);
        else if (gamepad1.b) findColorBall = new FindColorBall(carousel, colorSensor, BallColor.PURPLE);

        if (findColorBall == null || findColorBall.run())
            findColorBall = null;
    }

    private void handleOuttake() {
        if (gamepad1.a) lift.down();
        else if (gamepad1.y) lift.up();

        if (gamepad1.right_trigger != 0) shooter.start();
        else if (gamepad1.right_bumper) shooter.stop();
    }

    private void handleLights() {
        updateHeadLight.run();
    }
}
