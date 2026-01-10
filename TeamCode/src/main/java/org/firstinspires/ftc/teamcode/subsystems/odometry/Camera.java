package org.firstinspires.ftc.teamcode.subsystems.odometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;

public class Camera {
    private final Limelight3A limelight;
    private final ImuSensor imu;
    private final Telemetry telemetry;
    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = new ImuSensor(hardwareMap, telemetry);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        this.telemetry = telemetry;
    }

    public LLResult getDetection() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;
        return result;
    }

    public int getTagId() {
        LLResult detection = getDetection();
        if (detection == null || detection.getFiducialResults() == null || detection.getFiducialResults().isEmpty()) return 0;
        int id = detection.getFiducialResults().get(0).getFiducialId();
        telemetry.addData("April Tag Id", id);
        return id;
    }

    public double[] getTagXYArea() {
        LLResult detection = getDetection();
        if (detection == null) return new double[] {0, 0, 0};

        double[] xyArea = new double[] {
                detection.getTx(),
                detection.getTy(),
                detection.getTa()
        };

        telemetry.addData("Target X", xyArea[0]);
        telemetry.addData("Target Y", xyArea[1]);
        telemetry.addData("Target Area", xyArea[2]);

        return xyArea;
    }

    public Pose2d getBotPose() {
        LLResult detection = getDetection();
        if (detection == null) return null;

        double yawAngle = imu.getYaw().yaw;
        limelight.updateRobotOrientation(yawAngle);

        Pose3D botPose = detection.getBotpose();
        if (botPose == null) return null;

        // Returning in Roadrunner Coordinates
        return new Pose2d(
                botPose.getPosition().x,
                -botPose.getPosition().y,
                botPose.getOrientation().getYaw(AngleUnit.RADIANS)
        );
    }

    public void stop() {
        limelight.stop();
    }
}