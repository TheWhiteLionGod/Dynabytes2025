package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class TrackBall implements Action {
    Carousel carousel;
    ColorInput colorSensor;
    Instant timer = Instant.now();
    boolean initialized = false;

    public TrackBall(Carousel carousel, ColorInput colorSensor) {
        this.carousel = carousel;
        this.colorSensor = colorSensor;
    }

    @Override
    public boolean run() {
        if (!initialized) {
            resetState();
        }

        if (carousel.allPositionsFilled()) {
            return true;
        }

        int currentPos = getNextEmptyPosition();
        carousel.spin(getCarouselTarget(currentPos));

        if (hasSpinTimeElapsed()) {
            setColorForPosition(currentPos, detectColor());
            timer = Instant.now();
        }

        return false;
    }

    private void resetState() {
        carousel.pos1Color = null;
        carousel.pos2Color = null;
        carousel.pos3Color = null;
        timer = Instant.now();
        initialized = true;
    }

    private boolean hasSpinTimeElapsed() {
        return Duration.between(timer, Instant.now()).toMillis()
                >= Constants.CAROUSEL_SPIN_TIME;
    }

    private int getNextEmptyPosition() {
        if (carousel.pos1Color == null) return 1;
        if (carousel.pos2Color == null) return 2;
        return 3;
    }

    private double getCarouselTarget(int position) {
        switch (position) {
            case 1:
                return Constants.CAROUSEL_POS_1;
            case 2:
                return Constants.CAROUSEL_POS_2;
            case 3:
                return Constants.CAROUSEL_POS_3;
            default:
                throw new IllegalStateException("Invalid carousel position");
        }
    }

    private FindColorBall.Color detectColor() {
        if (colorSensor.isPurple()) return FindColorBall.Color.PURPLE;
        if (colorSensor.isGreen()) return FindColorBall.Color.GREEN;
        return FindColorBall.Color.EMPTY;
    }

    private void setColorForPosition(int position, FindColorBall.Color color) {
        switch (position) {
            case 1:
                carousel.pos1Color = color;
                break;

            case 2:
                carousel.pos2Color = color;
                break;

            case 3:
                carousel.pos3Color = color;
                break;

            default:
                throw new IllegalStateException("Invalid carousel position");
        }
    }
}
