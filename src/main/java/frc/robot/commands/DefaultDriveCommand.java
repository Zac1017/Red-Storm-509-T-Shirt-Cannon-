package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDrive;

public class DefaultDriveCommand extends Command{
    
    double forward;
    double right;
    
    private final TankDrive tankDrive;
    
    public DefaultDriveCommand (TankDrive tankDrive, double forward, double right) {
        this.forward = forward;
        this.right = right;
        this.tankDrive = tankDrive;
        addRequirements(tankDrive);
    }

    public void drive() {
        tankDrive.drive(forward, right);
    }
}
