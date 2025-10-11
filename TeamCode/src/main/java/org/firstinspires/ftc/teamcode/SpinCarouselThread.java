package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class SpinCarouselThread implements Runnable {
    ColorSensor colorSensor;
    Servo Carousel;
    int ball_color;
    final int GREEN_BALL = 0;
    final int PURPLE_BALL = 1;
    final int GREEN_MIN = 150;
    final int GREEN_MAX = 180;
    final int PURPLE_MIN = 200;
    final int PURPLE_MAX = 240;
    final double CAROUSEL_POS_1 = 0.0;
    final double CAROUSEL_POS_2 = 0.0;
    final double CAROUSEL_POS_3 = 0.0;
    final int SCALE_FACTOR = 255;
    float[] hsvValues = {0F, 0F, 0F};
    public SpinCarouselThread(ColorSensor colorSensor, Servo Carousel, int ball_color) {
        this.colorSensor = colorSensor;
        this.Carousel = Carousel;
        this.ball_color = ball_color;
    }
    public void updateHSV() {
        Color.RGBToHSV(
                (colorSensor.red() * SCALE_FACTOR),
                (colorSensor.green() * SCALE_FACTOR),
                (colorSensor.blue() * SCALE_FACTOR),
                hsvValues
        );
    }

    public void run() {
        switch (ball_color) {
            case GREEN_BALL:
                findGreenBall();
                break;

            case PURPLE_BALL:
                findPurpleBall();
                break;

            default:
                telemetry.addLine("INVALID BALL COLOR ENTERED");
                break;
        }
    }

    public void findGreenBall() {
        for (int i = 0; i < 3; i++) {
            updateHSV();
            if (hsvValues[0] >= GREEN_MIN && hsvValues[0] <= GREEN_MAX) {
                return;
            }
            spinCarousel();

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException err) {
                telemetry.addLine("Interrupt Exception Occurred");
            }
        }
        telemetry.addData("FAILED TO FIND GREEN BALL, HSV", hsvValues[0]);
        telemetry.update();
    }

    public void findPurpleBall() {
        for (int i = 0; i < 3; i++) {
            updateHSV();
            if (hsvValues[0] >= PURPLE_MIN && hsvValues[0] <= PURPLE_MAX) {
                return;
            }
            spinCarousel();

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException err) {
                telemetry.addLine("Interrupt Exception Occurred");
            }
        }
        telemetry.addData("FAILED TO FIND PURPLE BALL, HSV", hsvValues[0]);
        telemetry.update();
    }

    public void spinCarousel() {
        double cur_pos = Carousel.getPosition();
        if (cur_pos == CAROUSEL_POS_1) {
            Carousel.setPosition(CAROUSEL_POS_2);
        }
        else if (cur_pos == CAROUSEL_POS_2) {
            Carousel.setPosition(CAROUSEL_POS_3);
        }
        else if (cur_pos == CAROUSEL_POS_3) {
            Carousel.setPosition(CAROUSEL_POS_1);
        }
    }
}
