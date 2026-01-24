package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;
import org.firstinspires.ftc.teamcode.actions.FindColorBallNew;
import org.firstinspires.ftc.teamcode.actions.TrackBall;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

public class TrackBallTest extends Dynawheels {
    Action trackBallAction, findColorBallAction;

    @Override
    public void config() {
        carousel = new Carousel(hardwareMap, telemetry);
        colorSensor = new ColorInput(hardwareMap, telemetry);
    }

    @Override
    public void run() {
        // Handling Inputs
        if (gamepad1.dpad_down) carousel.spin();
        else if (gamepad1.dpad_left)
            findColorBallAction = new FindColorBallNew(carousel, colorSensor, FindColorBall.Color.GREEN);
        else if (gamepad1.dpad_right)
            findColorBallAction = new FindColorBallNew(carousel, colorSensor, FindColorBall.Color.PURPLE);
        else if (gamepad1.dpad_up)
            trackBallAction = new TrackBall(carousel, colorSensor);

        // Handling Actions
        if (findColorBallAction != null && findColorBallAction.run())
            findColorBallAction = null;

        // Track Ball Action is Else If Because Both Shouldn't Run At The Same Time
        else if (trackBallAction != null && trackBallAction.run())
            trackBallAction = null;

        telemetry.update();
        sleep(50);
    }

    @Override
    public void cleanup() {

    }
}
