package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;
import org.firstinspires.ftc.teamcode.actions.UpdateHeadLight;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;

//@Disabled
@TeleOp(name="Two Player Controller", group="FTC2026")
public class TwoPlayerCtrl extends Dynawheels {
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
        if (gamepad1.left_stick_y != 0 ||
                gamepad1.left_stick_x != 0 ||
                gamepad1.right_stick_x != 0) {

            drivetrain.robotDrive(
                    -gamepad1.left_stick_y * 0.85,
                    gamepad1.left_stick_x * 0.85,
                    gamepad1.right_stick_x * 0.5);
            gate.close(); // Closing Gate When Robot Is Moving
        }
        else {
            drivetrain.stop();
            gate.open(); // Opening Gate When Robot Isn't Moving
        }
    }

    private void handleIntake() {
        if (gamepad2.left_trigger != 0) roller.forward();
        else if (gamepad2.left_bumper) roller.reverse();
        else if (gamepad2.a) roller.stop();
    }

    private void handleCarousel() {
        if (gamepad2.dpad_down) {
            lift.down();
            sleep(200);

            carousel.spin();
            findColorBall = null; // Stopping Carousel Running Actions
        }

        else if (gamepad2.dpad_left) {
            lift.down();
            sleep(200);

            findColorBall = new FindColorBall(carousel, colorSensor, BallColor.GREEN);
        }

        else if (gamepad2.dpad_right) {
            lift.down();
            sleep(200);

            findColorBall = new FindColorBall(carousel, colorSensor, BallColor.PURPLE);
        }

        if (findColorBall == null || findColorBall.run())
            findColorBall = null;
    }

    private void handleOuttake() {
        if (gamepad2.x) lift.up();
        else if (gamepad2.b) lift.down();

        if (gamepad2.right_trigger != 0) shooter.start();
        else if (gamepad2.right_bumper) shooter.stop();

        if (gamepad1.yWasPressed()) shooter.increasePwr();
        else if (gamepad1.aWasPressed()) shooter.decreasePwr();

        telemetry.addData("Shooter", shooter.motorPwr);
    }

    private void handleLights() {
        updateHeadLight.run();
    }
}
