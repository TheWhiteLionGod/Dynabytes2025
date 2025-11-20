package org.firstinspires.ftc.teamcode.subsystems.odometry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Camera {
    private final Limelight3A limelight;
    private final Telemetry telemetry;
    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        this.telemetry = telemetry;
    }

    public List<LLResultTypes.FiducialResult> getDetection() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;
        return result.getFiducialResults();
    }

    public int getTagId() {
        int id = getDetection().get(0).getFiducialId();
        telemetry.addData("April Tag Id", id);
        return id;
    }

    public double[] getTagXYArea() {
        List<LLResultTypes.FiducialResult> detection = getDetection();
        double[] xyArea = new double[] {
                detection.get(0).getTargetXPixels(),
                detection.get(0).getTargetYPixels(),
                detection.get(0).getTargetArea()
        };

        telemetry.addData("Target X", xyArea[0]);
        telemetry.addData("Target Y", xyArea[1]);
        telemetry.addData("Target Area", xyArea[2]);

        return xyArea;
    }

    public void stop() {
        limelight.stop();
    }
}