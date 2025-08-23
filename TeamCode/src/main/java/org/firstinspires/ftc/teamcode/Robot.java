package org.firstinspires.ftc.teamcode;

// Base Robot Methods that All Robot Should Implement
public interface Robot {
    // Robot Configuration(Motors, Servos, Etc)
    void configureRobot();

    // Drivetrain Movements
    void moveDriveTrain(double pwrx, double pwry);
    void turnDriveTrain(double pwr);
    void resetDriveTrain();
}

