package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

public class FindColorBallNewV2 implements Action {
    Carousel carousel;
    ColorInput colorSensor;
    BallColor ballColor;

    public FindColorBallNewV2(Carousel carousel, ColorInput colorSensor, BallColor ballColor) {
        this.carousel = carousel;
        this.colorSensor = colorSensor;
        this.ballColor = ballColor;

    }

    @Override
    public boolean run() {
        // REQUIRES TRACK BALL ACTION TO LOCATE BALL COLORS
        (new TrackBallV2(carousel, colorSensor)).run();

        if (carousel.pos1Color == ballColor)
            carousel.spin(Constants.CAROUSEL_POS_1);
        else if (carousel.pos2Color == ballColor)
            carousel.spin(Constants.CAROUSEL_POS_2);
        else if (carousel.pos3Color == ballColor)
            carousel.spin(Constants.CAROUSEL_POS_3);

        return true;
    }
}
