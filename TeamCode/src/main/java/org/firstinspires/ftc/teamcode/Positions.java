package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public enum Positions {
    START(0, 0, 0),
    RED_DOWN(-5.25 * 12, -2.75 * 12, Math.toRadians(0)),
    BLUE_DOWN(-5.25 * 12, 2.75 * 12, Math.toRadians(0)),
    RED_UP(4*12, -4*12, Math.toRadians(135)),
    BLUE_UP(4*12, 4*12, Math.toRadians(225));


    private final double x, y, heading;

    Positions(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Pose2d getPose2D() {
        return new Pose2d(x, y, heading);
    }
}
