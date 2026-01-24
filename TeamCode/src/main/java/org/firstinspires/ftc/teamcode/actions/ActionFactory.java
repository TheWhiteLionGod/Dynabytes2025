package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;

public final class ActionFactory {
    private static Action updateHeadLights;
    public static Action turnDrivetrainTo(Dynawheels robot, Coords targetYaw) {
        return new TurnDrivetrainTo(robot.drivetrain, robot.imu, targetYaw);
    }

    public static Action updateHeadLights(Dynawheels robot) {
        if (updateHeadLights == null)
            updateHeadLights = new UpdateHeadLight(robot.headLight, robot.colorSensor, robot.lift);
        return updateHeadLights;
    }

    public static Action shootBall(Dynawheels robot) {
        return new ShootBall(robot.shooter, robot.lift);
    }

    public static Action findColorBall(Dynawheels robot, FindColorBall.Color color) {
        return new FindColorBall(robot.carousel, robot.colorSensor, color);
    }
}
