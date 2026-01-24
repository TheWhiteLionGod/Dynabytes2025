package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

public class FindColorBallNew implements Action {
    Carousel carousel;
    ColorInput colorSensor;
    FindColorBall.Color ballColor;
    TrackBall trackBallAction;

    public FindColorBallNew(Carousel carousel, ColorInput colorSensor, FindColorBall.Color ballColor) {
        this.carousel = carousel;
        this.colorSensor = colorSensor;
        this.ballColor = ballColor;

    }

    @Override
    public boolean run() {
        // REQUIRES TRACK BALL ACTION TO LOCATE BALL COLORS
        if (!carousel.allPositionsFilled()) {
            if (trackBallAction == null) {
                trackBallAction = new TrackBall(carousel, colorSensor);
            }

            trackBallAction.run();
            return false;
        }

        if (carousel.pos1Color == ballColor)
            carousel.spin(Constants.CAROUSEL_POS_1);
        else if (carousel.pos2Color == ballColor)
            carousel.spin(Constants.CAROUSEL_POS_2);
        else if (carousel.pos3Color == ballColor)
            carousel.spin(Constants.CAROUSEL_POS_3);

        return true;
    }
}
