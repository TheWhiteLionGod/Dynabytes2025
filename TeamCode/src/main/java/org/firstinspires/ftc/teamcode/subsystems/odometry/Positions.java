package org.firstinspires.ftc.teamcode.subsystems.odometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public enum Positions {
    // Starting Positions
    START(new Pose2d(0, 0, 0)),
    RED_DOWN(new Pose2d(-5.25 * 12, -0.75 * 12, Math.toRadians(0))),
    BLUE_DOWN(new Pose2d(-5.25 * 12, 0.75 * 12, Math.toRadians(0))),
    RED_UP(new Pose2d(4*12, -4*12, Math.toRadians(135))),
    BLUE_UP(new Pose2d(4*12, 4*12, Math.toRadians(225))),
    // Bases
    RED_BASE(new Pose2d(-3.25*12, 2.75*12, Math.toRadians(0))),
    BLUE_BASE(new Pose2d(-3.25*12, -2.75*12, Math.toRadians(0))),
    // Scoring Positions
    SCORE_RED(new Pose2d(0, 0, Math.toRadians(135))),
    SCORE_BLUE(new Pose2d(0, 0, Math.toRadians(225))),
    // April Tag Locations
    BLUE_TAG(new Pose2d(4.86 * 12, 4.64 * 12, Math.toRadians(45))),
    RED_TAG(new Pose2d(4.86 * 12, -4.64 * 12, Math.toRadians(315))),
    OBELISK(new Pose2d(6 * 12, 0, Math.toRadians(0))),
    // Ball Locations
    RED_GPP(new Pose2d(-3 * 12, -4 * 12, Math.toRadians(270))),
    RED_PGP(new Pose2d(-1 * 12, -4 * 12, Math.toRadians(270))),
    RED_PPG(new Pose2d(12, -4 * 12, Math.toRadians(270))),
    BLUE_PPG(new Pose2d(-3 * 12, 4 * 12, Math.toRadians(90))),
    BLUE_PGP(new Pose2d(-1 * 12, 4 * 12, Math.toRadians(90))),
    BLUE_GPP(new Pose2d(12, 4 * 12, Math.toRadians(90))),
    // Camera Offset
    CAMERA(new Pose2d(0, 0, Math.toRadians(0)));

    public final Pose2d pos;

    Positions(Pose2d pos) {
        this.pos = pos;
    }

    public Pose2d get() {
        return pos;
    }
}
