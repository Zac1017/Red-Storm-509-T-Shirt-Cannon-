package frc.robot.commands;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command {


    Shooter shooter;
    double elevation;

    public ShooterCommand(Shooter shooter, double elevation) {
        this.shooter = shooter;
        this.elevation = elevation;
        addRequirements(shooter);
    }

    @Override
    public void execute() {

        Commands.sequence(

                //elevate shooter according to button pressed
                Commands.runOnce(() -> shooter.elevateShooter(elevation)),

                //rotate shooter
                Commands.runOnce(() -> shooter.rotate(Constants.ShooterConstants.kRotation)),   
        
                //seal barrel solenoid
                //open charge tank solenoid
                //close charge tank solenoid
                //open chamber tank solenoid
                //close chamber tank solenoid
                //t-shirt fires

                Commands.runOnce(() -> shooter.close(shooter.kSealSolenoid)),
                Commands.waitSeconds(2.0),

                Commands.runOnce(() -> shooter.open(shooter.kChargeTankSolenoid)),
                Commands.waitSeconds(2.0),

                Commands.runOnce(() -> shooter.close(shooter.kChargeTankSolenoid)),
                Commands.waitSeconds(1.0),

                Commands.runOnce(() -> shooter.open(shooter.kChamberTankSolenoid)),
                Commands.waitSeconds(2.0),

                Commands.runOnce(() -> shooter.close(shooter.kChamberTankSolenoid))
                
                ).schedule();

   

    }

    

    
}
