package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

@Disabled
//@TeleOp(name="Color Sensor Test", group="TEST")
public class ColorTest extends Dynawheels {
    Action findColorBall;
    @Override
    public void config() {
        carousel = new Carousel(hardwareMap, telemetry);
        telemetry.update();
    }

    @Override
    public void run() {
        if (gamepad1.dpad_down) {
            carousel.spin();
        }
        else if (gamepad1.dpad_left) {
            carousel.spin(Constants.CAROUSEL_POS_1);
        }
        else if (gamepad1.dpad_up) {
            carousel.spin(Constants.CAROUSEL_POS_2);
        }
        else if (gamepad1.dpad_right) {
            carousel.spin(Constants.CAROUSEL_POS_3);
        }
        else if (gamepad1.x) {
            findColorBall = new FindColorBall(carousel, colorSensor, BallColor.GREEN);
        }
        else if (gamepad1.b) {
            findColorBall = new FindColorBall(carousel, colorSensor, BallColor.PURPLE);
        }

        if (findColorBall == null || findColorBall.run()) {
            findColorBall = null;
        }
    }

    @Override
    public void cleanup() {}
}
