package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.FindColorBall;
import org.firstinspires.ftc.teamcode.actions.TrackBall;
import org.firstinspires.ftc.teamcode.actions.UpdateHeadLight;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.firstinspires.ftc.teamcode.subsystems.transfer.HeadLight;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;

@TeleOp(name="Color Test", group="TEST")
public class ColorTest extends Dynawheels {
    Action trackBallAction, findColorBallAction, updateHeadLight;

    @Override
    public void config() {
        carousel = new Carousel(hardwareMap, telemetry);
        colorSensor = new ColorInput(hardwareMap, telemetry);
        lift = new Lift(hardwareMap, telemetry);
        headLight = new HeadLight(hardwareMap, telemetry);
        updateHeadLight = new UpdateHeadLight(headLight, colorSensor, lift);
        trackBallAction = new TrackBall(carousel, colorSensor);
        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            // Handling Inputs
            if (gamepad1.dpad_down) carousel.spin();
            else if (gamepad1.dpad_left)
                findColorBallAction = new FindColorBall(carousel, colorSensor, BallColor.GREEN);
            else if (gamepad1.dpad_right)
                findColorBallAction = new FindColorBall(carousel, colorSensor, BallColor.PURPLE);

            // Handling Actions
            if (findColorBallAction != null && findColorBallAction.run())
                findColorBallAction = null;

            trackBallAction.run();
            updateHeadLight.run();

            telemetry.addData("Pos 1 Color", carousel.pos1Color.toString());
            telemetry.addData("Pos 2 Color", carousel.pos2Color.toString());
            telemetry.addData("Pos 3 Color", carousel.pos3Color.toString());

            telemetry.update();
            sleep(50);
        }
    }

    @Override
    public void cleanup() {

    }
}
