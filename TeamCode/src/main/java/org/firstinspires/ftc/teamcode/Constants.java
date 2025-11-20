package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Constants {
    // Carousel Position
    public static final double CAROUSEL_POS_1 = 0.04;
    public static final double CAROUSEL_POS_2 = 0.51;
    public static final double CAROUSEL_POS_3 = 0.94;

    // Lift Position
    public static final double LIFT_DOWN_POS = 0.0;
    public static final double LIFT_UP_POS = 0.22;

    // Carousel Spin Time
    public static final int CAROUSEL_SPIN_TIME = 250;
    // Shooter Spin Time
    public static final int SHOOTER_SPIN_TIME = 1000;
    // Lift Up Time
    public static final int LIFT_UP_TIME = 100;

    // Hue Values
    public static final int GREEN_MIN = 150;
    public static final int GREEN_MAX = 180;
    public static final int PURPLE_MIN = 200;
    public static final int PURPLE_MAX = 240;
    // April Tag Id
    public static final int BLUE_TAG = 20;
    public static final int RED_TAG = 24;
    public static final int GPP_TAG = 21;
    public static final int PGP_TAG = 22;
    public static final int PPG_TAG = 23;
    // Auto TeleOp Position Transfer
    public static Pose2d currentPose = new Pose2d();
}
