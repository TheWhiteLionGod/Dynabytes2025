package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class PositionStorage {
    public static Pose2d start_pos = new Pose2d(0, 0,0);
    public static Pose2d red_down_pos = new Pose2d(-5.25 * 12, -2.75 * 12, Math.toRadians(0));
    public static Pose2d blue_down_pos = new Pose2d(-5.25 * 12, 2.75 * 12, Math.toRadians(0));
    public static Pose2d red_up_pos = new Pose2d(4*12, -4*12, Math.toRadians(135));
    public static Pose2d blue_up_pos = new Pose2d(4*12, 4*12, Math.toRadians(225));
}
