package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class SpinCarouselThread implements Runnable {
    ColorSensor colorSensor;
    Servo Carousel;
    int ball_color;
    float[] hsvValues = {0F, 0F, 0F};
    public SpinCarouselThread(ColorSensor colorSensor, Servo Carousel, int ball_color) {
        this.colorSensor = colorSensor;
        this.Carousel = Carousel;
        this.ball_color = ball_color;
    }
    public void updateHSV() {
        Color.RGBToHSV(
                (colorSensor.red() * Constants.HUE_SCALE_FACTOR),
                (colorSensor.green() * Constants.HUE_SCALE_FACTOR),
                (colorSensor.blue() * Constants.HUE_SCALE_FACTOR),
                hsvValues
        );
    }

    public void run() {
        switch (ball_color) {
            case Constants.GREEN_BALL:
                findGreenBall();
                break;

            case Constants.PURPLE_BALL:
                findPurpleBall();
                break;

            default:
                break;
        }
    }

    public void findGreenBall() {
        for (int i = 0; i < 3; i++) {
            updateHSV();
            if (hsvValues[0] >= Constants.GREEN_HUE_MIN
                    && hsvValues[0] <= Constants.GREEN_HUE_MAX) {
                return;
            }
            spinCarousel();

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ignored) {

            }
        }
    }

    public void findPurpleBall() {
        for (int i = 0; i < 3; i++) {
            updateHSV();
            if (hsvValues[0] >= Constants.PURPLE_HUE_MIN
                    && hsvValues[0] <= Constants.PURPLE_HUE_MAX) {
                return;
            }
            spinCarousel();

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ignored) {

            }
        }
    }

    public void spinCarousel() {
        double cur_pos = Carousel.getPosition();
        if (cur_pos == Constants.CAROUSEL_POS_1) {
            Carousel.setPosition(Constants.CAROUSEL_POS_2);
        }
        else if (cur_pos == Constants.CAROUSEL_POS_2) {
            Carousel.setPosition(Constants.CAROUSEL_POS_3);
        }
        else if (cur_pos == Constants.CAROUSEL_POS_3) {
            Carousel.setPosition(Constants.CAROUSEL_POS_1);
        }
    }
}
