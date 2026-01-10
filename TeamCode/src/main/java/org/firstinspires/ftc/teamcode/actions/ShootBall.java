package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;

public class ShootBall implements Action {
    Shooter shooter;
    Lift lift;

    public ShootBall(Shooter shooter, Lift lift) {
        this.shooter = shooter;
        this.lift = lift;
    }

    @Override
    public void run() {
        shooter.forward();
        try { Thread.sleep(Constants.SHOOTER_SPIN_TIME); }
        catch (InterruptedException ignored) {}

        lift.up();

        try { Thread.sleep(Constants.LIFT_UP_TIME); }
        catch (InterruptedException ignored) {}

        shooter.stop();
        lift.down();
    }
}
