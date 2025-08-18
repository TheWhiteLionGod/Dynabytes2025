package org.firstinspires.ftc.teamcode;

// Autonomous Specific Methods(MAY BE DEPRECATED DUE TO ROAD RUNNER)
public interface Autonomous extends Robot {
    // Drivetrain Movement Functions
    public default void moveForward(double pwr) {
        moveDriveTrain(Math.abs(pwr), 0);
    }

    public default void moveBackward(double pwr) {
        moveDriveTrain(-Math.abs(pwr), 0);
    }

    public default void moveLeft(double pwr) {
        moveDriveTrain(0, -Math.abs(pwr));
    }
    
    public default void moveRight(double pwr) {
        moveDriveTrain(0, Math.abs(pwr));
    }

    // Simple Unit Conversion Functions for Ease of Use
    public default double feetToInches(double feet) {
        return feet * 12.0;
    }

    // Extra Utility Function. REMEMBER: Road Runner Takes In Trajectories as INCHES, not Feet.
    public default double inchesToFeet(double inches) {
        return inches / 12.0;
    }
}
