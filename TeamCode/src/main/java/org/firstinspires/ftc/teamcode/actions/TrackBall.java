package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.BallColor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

public class TrackBall implements Action {
    Carousel carousel;
    ColorInput colorSensor;

    public TrackBall(Carousel carousel, ColorInput colorSensor) {
        this.carousel = carousel;
        this.colorSensor = colorSensor;
    }

    @Override
    public boolean run() {
        boolean[] isGreen = colorSensor.isGreen();
        boolean[] isPurple = colorSensor.isPurple();

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
