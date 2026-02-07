package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Constants {
    public static boolean approxEqualTo(double a, double b) {
        return Math.abs(a - b) < 0.001;
    }

    // Carousel Position
    public static final double CAROUSEL_POS_1 = 0.04;
    public static final double CAROUSEL_POS_2 = 0.51;
    public static final double CAROUSEL_POS_3 = 0.94;

    // Lift Position
    public static final double LIFT_DOWN_POS = 0.0;
    public static final double LIFT_UP_POS = 0.095;
    public static final int LIFT_DELAY_TIME = 100;

    // Hue Values
    public static final int GREEN_MIN = 195;
    public static final int GREEN_MAX = 210;
    public static final int PURPLE_MIN = 120;
    public static final int PURPLE_MAX = 150;
    // April Tag Id
    public static final int BLUE_TAG = 20;
    public static final int RED_TAG = 24;
    public static final int GPP_TAG = 21;
    public static final int PGP_TAG = 22;
    public static final int PPG_TAG = 23;
    // Shooter PIDF Coefficients
    public static final PIDFCoefficients shooterPIDF = new PIDFCoefficients(1.0, 0, 0, 12.051);
    public static final double shooterMaxVel = 2500;
}
