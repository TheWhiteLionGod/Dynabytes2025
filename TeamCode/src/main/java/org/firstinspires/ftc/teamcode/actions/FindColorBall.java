package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class FindColorBall implements Action {
    enum State {
        IDLE, SPINNING, FINDING_GREEN, FINDING_PURPLE
    }

    public enum Color {
        GREEN, PURPLE
    }

    Carousel carousel;
    ColorInput colorSensor;
    State state = State.IDLE;
    Color ballColor;
    Instant timer = Instant.now();
    int attempts = 0;

    public FindColorBall(Carousel carousel, ColorInput colorSensor, Color ballColor) {
        this.carousel = carousel;
        this.colorSensor = colorSensor;
        this.ballColor = ballColor;
    }

    @Override
    public boolean run() {
        switch (state) {
            case IDLE:
                if (ballColor == Color.GREEN) {
                    state = State.FINDING_GREEN;
                }
                else if (ballColor == Color.PURPLE) {
                    state = State.FINDING_PURPLE;
                }
                break;

            case SPINNING:
                if (Duration.between(timer, Instant.now()).toMillis() > Constants.CAROUSEL_SPIN_TIME) {
                    state = State.IDLE;
                }
                else carousel.spin();
                break;

            case FINDING_GREEN:
                if (colorSensor.isGreen() || attempts >= 3) return true;

                state = State.SPINNING;
                attempts++;
                break;

            case FINDING_PURPLE:
                if (colorSensor.isPurple() || attempts >= 3) return true;

                state = State.SPINNING;
                attempts++;
                break;
        }

        return false;
    }
}
