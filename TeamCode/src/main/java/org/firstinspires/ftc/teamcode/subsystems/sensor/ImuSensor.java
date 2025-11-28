package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ImuSensor {
    private final IMU imu;
    private final Telemetry telemetry;

    public ImuSensor(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        )));
        imu.resetYaw();

        this.telemetry = telemetry;
    }

    public double getYawDegrees() {
        double yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        return yaw < 0 ? 360 + yaw : yaw;
    }

    public double getYawRadians() {
        return Math.toRadians(getYawDegrees());
    }

}
