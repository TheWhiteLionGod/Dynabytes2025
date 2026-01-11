package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.transfer.CarouselOld;
import org.firstinspires.ftc.teamcode.subsystems.transfer.HeadLight;
@Disabled
//@TeleOp(name="Color Sensor Test", group="TEST")
public class ColorTest extends Dynawheels {
    HeadLight headLight;
    @Override
    public void config() {
        carousel = new CarouselOld(hardwareMap, telemetry);
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
            carousel.findGreenBall();
        }
        else if (gamepad1.b) {
            carousel.findPurpleBall();
        }

        carousel.updateState();
    }

    @Override
    public void cleanup() {
        carousel.stop();
    }
}
