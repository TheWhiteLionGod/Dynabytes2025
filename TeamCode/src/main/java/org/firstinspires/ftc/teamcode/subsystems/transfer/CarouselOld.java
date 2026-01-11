package org.firstinspires.ftc.teamcode.subsystems.transfer;

import org.firstinspires.ftc.teamcode.Constants;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;


public class CarouselOld {
    public enum States {
        IDLE, SPINNING, FINDING_GREEN, FINDING_PURPLE
    }

    private final Servo carousel;
    private final Telemetry telemetry;
    private final ColorInput colorSensor;
    private final HeadLight headLight;
    Instant carouselSpinTime = Instant.now();
    States state = States.IDLE;
    States returnState = States.IDLE;
    int attempts = 0;

    public CarouselOld(HardwareMap hardwareMap, Telemetry telemetry) {
        carousel = hardwareMap.get(Servo.class, "Carousel");
        carousel.setPosition(Constants.CAROUSEL_POS_1);

        colorSensor = new ColorInput(hardwareMap, telemetry);
        headLight = new HeadLight(hardwareMap, telemetry);

        this.telemetry = telemetry;
    }

    public void spin() {
        if (state == States.IDLE) {
            double curPos = carousel.getPosition();

            // Checking if Approx Equal To due to Floating Point Error
            if (Constants.approxEqualTo(curPos, Constants.CAROUSEL_POS_1)) {
                carousel.setPosition(Constants.CAROUSEL_POS_2);
            }
            else if (Constants.approxEqualTo(curPos, Constants.CAROUSEL_POS_2)) {
                carousel.setPosition(Constants.CAROUSEL_POS_3);
            }
            else if (Constants.approxEqualTo(curPos, Constants.CAROUSEL_POS_3)) {
                carousel.setPosition(Constants.CAROUSEL_POS_1);
            }
            else {
                carousel.setPosition(Constants.CAROUSEL_POS_1);
            }

            carouselSpinTime = Instant.now();
            state = States.SPINNING;
            returnState = States.IDLE;
        }
    }

    public void spin(double pos) {
        if (state == States.IDLE) {
            carousel.setPosition(pos);
            carouselSpinTime = Instant.now();

            state = States.SPINNING;
            returnState = States.IDLE;
        }
    }

    public void findGreenBall() {
        if (state == States.IDLE) {
            state = States.FINDING_GREEN;
        }
    }

    public void findPurpleBall() {
        if (state == States.IDLE) {
            state = States.FINDING_PURPLE;
        }
    }

    public void updateState() {
        switch (state) {
            case IDLE:
                // Updating Headlight if Carousel is Idle
                if (isGreen()) {
                    headLight.greenOn();
                }
                else if (isPurple()) {
                    headLight.purpleOn();
                }
                else {
                    headLight.stop();
                }

            case SPINNING:
                // Checking If Its Been 250 Milliseconds Since Spin
                if (Duration.between(carouselSpinTime, Instant.now()).toMillis()
                        > Constants.CAROUSEL_SPIN_TIME) {
                    state = returnState;
                }
                // Telemetry if Carousel is Spinning
                else {
                    telemetry.addData("Spinning Carousel", carousel.getPosition());
                }
                break;

            case FINDING_GREEN:
                if (isGreen() || attempts >= 3) {
                    stop();
                    break;
                }

                state = States.IDLE;
                spin();

                returnState = States.FINDING_GREEN;
                attempts++;
                break;

            case FINDING_PURPLE:
                if (isPurple() || attempts >= 3) {
                    stop();
                    break;
                }

                state = States.IDLE;
                spin();

                returnState = States.FINDING_PURPLE;
                attempts++;
                break;
        }
    }

    public boolean isGreen() {
        return Constants.GREEN_MIN <= colorSensor.hue()
                && colorSensor.hue() <= Constants.GREEN_MAX;
    }

    public boolean isPurple() {
        return Constants.PURPLE_MIN <= colorSensor.hue()
                && colorSensor.hue() <= Constants.PURPLE_MAX;
    }

    public boolean isIdle() {
        return state == States.IDLE;
    }

    public void stop() {
        returnState = States.IDLE;
        state = States.IDLE;
        attempts = 0;
        headLight.stop();
    }
}
