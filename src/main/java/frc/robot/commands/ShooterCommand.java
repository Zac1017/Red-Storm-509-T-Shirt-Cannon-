package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command {

    boolean compressorBoolean;
    Shooter shooter;

    Solenoid compressorSolenoid = new Solenoid(null, 0);
    Solenoid chamberSolenoid = new Solenoid(null, 0);

    public ShooterCommand(Shooter shooter, BooleanSupplier compressorBooleanSupplier){
        this.compressorBoolean = compressorBooleanSupplier.getAsBoolean();
        this.shooter = shooter;
        addRequirements(shooter);
    }

    public void shoot(){
        if (compressorBoolean) {
            shooter.close(chamberSolenoid);
            shooter.open(compressorSolenoid);
        } else {
            shooter.close(compressorSolenoid);
            shooter.open(chamberSolenoid);
        }
    }
}
