package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Coords {
    public enum Unit {
        Imu, RoadRunner
    }
    public double x, y, yaw;
    public Unit unit;

    public Coords(double x, double y, double yaw, Unit unit) {
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.unit = unit;
    }

    public Coords(double yaw, Unit unit) {
        this.yaw = yaw;
        this.unit = unit;
    }

    public Coords toImu() {
        if (unit == Unit.Imu) return this;
        return new Coords(x, y, (yaw <= 180) ? yaw : -(360 - yaw), Unit.Imu);
    }

    public Coords toRoadRunner() {
        if (unit == Unit.RoadRunner) return this;
        return new Coords(x, y, (yaw >= 0) ? yaw : yaw + 360, Unit.RoadRunner);
    }

    public Pose2d toPose() {
        return new Pose2d(x, y, Math.toRadians(this.toRoadRunner().yaw));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coords)) return false;
        Coords coords = (Coords) obj;

        return coords.x == this.x &&
                coords.y == this.y &&
                coords.yaw == this.yaw &&
                coords.unit == this.unit;
    }
}
