package org.firstinspires.ftc.teamcode.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class Shooter {
    public enum States {
        IDLE, SPINNING, READY, FINISHED
    }
    public final Lift lift;
    private final DcMotorEx shooter;
    private final Telemetry telemetry;
    private Instant shootTime = Instant.now();
    private States state = States.IDLE;
    public boolean stopMotor = true;
    public double speed = 0.9;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        shooter = hardwareMap.get(DcMotorEx.class, "Launcher");
        shooter.setDirection(DcMotorEx.Direction.REVERSE);
        lift = new Lift(hardwareMap, telemetry);
        this.telemetry = telemetry;
    }

    public void start() {
        if (state == States.IDLE) {
            state = States.SPINNING;
            shootTime = Instant.now();
        }
    }

    public void stop() {
        state = States.IDLE;
        if (stopMotor) shooter.setPower(0);
        lift.down();
    }

    public void increaseSpeed() {
        speed += 0.1;
    }

    public void decreaseSpeed() {
        speed -= 0.1;
    }

    public boolean isIdle() {
        return state == States.IDLE;
    }

    public void updateState() {
        switch (state) {
            case IDLE:
                break;

            case SPINNING:
                // Checking If Its Been 1 Seconds Since Spin
                if (Duration.between(shootTime, Instant.now()).toMillis()
                        > Constants.SHOOTER_SPIN_TIME) {
                    state = States.READY;
                    shootTime = Instant.now();
                }
                // Telemetry if Shooter is Spinning
                else {
                    shooter.setPower(0.9);
                    telemetry.addData("Spinning Shooter", 1);
                }
                break;

            case READY:
                if (Duration.between(shootTime, Instant.now()).toMillis()
                    < Constants.LIFT_UP_TIME) {
                    lift.up();
                }
                else {
                    state = States.FINISHED;
                }
                break;

            case FINISHED:
                stop();
                break;
        }
    }
}
