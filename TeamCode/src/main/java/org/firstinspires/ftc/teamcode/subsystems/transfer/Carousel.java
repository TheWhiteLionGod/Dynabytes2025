package org.firstinspires.ftc.teamcode.subsystems.transfer;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

public class Carousel {
    private final Servo carousel;
    private final Telemetry telemetry;

    public BallColor pos1Color;
    public BallColor pos2Color;
    public BallColor pos3Color;


    public Carousel(HardwareMap hardwareMap, Telemetry telemetry) {
        carousel = hardwareMap.get(Servo.class, "Carousel");
        carousel.setPosition(Constants.CAROUSEL_POS_1);

        this.telemetry = telemetry;
        telemetry.addData("Carousel", "Initialized");
    }

    public void spin() {
        double curPos = carousel.getPosition();

        if (Constants.approxEqualTo(curPos, Constants.CAROUSEL_POS_1))
            spin(Constants.CAROUSEL_POS_2);
        else if (Constants.approxEqualTo(curPos, Constants.CAROUSEL_POS_2))
            spin(Constants.CAROUSEL_POS_3);
        else if (Constants.approxEqualTo(curPos, Constants.CAROUSEL_POS_3))
            spin(Constants.CAROUSEL_POS_1);
        else spin(Constants.CAROUSEL_POS_1);
    }

    public synchronized void spin(double pos) {
        carousel.setPosition(Range.clip(pos, 0, 1));
        telemetry.addData("Carousel", "Spinning to " + pos);
    }

    public boolean allPositionsFilled() {
        return pos1Color != null
                && pos2Color != null
                && pos3Color != null;
    }
}
