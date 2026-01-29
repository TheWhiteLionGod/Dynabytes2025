package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class TrackBallV2 implements Action {
    Carousel carousel;
    ColorInput colorSensor;

    public TrackBallV2(Carousel carousel, ColorInput colorSensor) {
        this.carousel = carousel;
        this.colorSensor = colorSensor;
    }

    @Override
    public boolean run() {
        boolean[] isGreen = colorSensor.isGreenV2();
        boolean[] isPurple = colorSensor.isPurpleV2();

        BallColor[] ballColors = new BallColor[3];

        for (int i = 0; i < 3; i++) {
            if (isGreen[i]) ballColors[i] = BallColor.GREEN;
            else if (isPurple[i]) ballColors[i] = BallColor.PURPLE;
            else ballColors[i] = BallColor.EMPTY;
        }

        carousel.pos1Color = ballColors[0];
        carousel.pos2Color = ballColors[1];
        carousel.pos3Color = ballColors[2];

        return true;
    }
}
