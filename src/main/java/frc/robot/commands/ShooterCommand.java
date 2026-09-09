package frc.robot.commands;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends SequentialCommandGroup {


    Shooter shooter;
    double elevation;

    public ShooterCommand(Shooter shooter, double elevation) {
        this.shooter = shooter;
        this.elevation = elevation;
        addRequirements(shooter);

        addCommands(
        
            Commands.runOnce(() -> shooter.elevateShooter(elevation)),
            Commands.runOnce(() -> shooter.rotate(Constants.ShooterConstants.kRotation)),
            Commands.runOnce(() -> shooter.close(shooter.kSealSolenoid)),
            Commands.waitSeconds(2.0),
            Commands.runOnce(() -> shooter.open(shooter.kChargeTankSolenoid)),
            Commands.waitSeconds(2.0),
            Commands.runOnce(() -> shooter.close(shooter.kChargeTankSolenoid)),
            Commands.waitSeconds(1.0),
            Commands.runOnce(() -> shooter.open(shooter.kChamberTankSolenoid)),
            Commands.waitSeconds(2.0),
            Commands.runOnce(() -> shooter.close(shooter.kChamberTankSolenoid)),
            Commands.runOnce(() -> shooter.elevateShooter(Constants.ShooterConstants.kdefaultElevation))

        );
    }
    
}
