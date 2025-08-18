package org.firstinspires.ftc.teamcode;

// Base Robot Methods that All Robot Should Implement
public interface Robot {
    // Robot Configuration(Motors, Servos, Etc)
    public void configureRobot();

    // Drivetrain Movements
    public void moveDriveTrain(double pwrx, double pwry);
    public void turnDriveTrain(double pwr);
    public void resetDriveTrain();
}

