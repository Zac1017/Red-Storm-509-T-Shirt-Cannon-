package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDrive;

public class DefaultDriveCommand extends Command{
    
    double forward;
    double right;
    
    private final TankDrive tankDrive;
    
    public DefaultDriveCommand (TankDrive tankDrive, DoubleSupplier forward, DoubleSupplier right) {
        this.forward = forward.getAsDouble();
        this.right = right.getAsDouble();
        this.tankDrive = tankDrive;
        addRequirements(tankDrive);
    }

    public void drive() {
        tankDrive.drive(forward, right);
    }
}
