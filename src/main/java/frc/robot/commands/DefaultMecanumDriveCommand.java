package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.MecanumDrive;

public class DefaultMecanumDriveCommand extends Command {
    
    private final MecanumDrive mecanum;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier omegaSupplier;
    private final BooleanSupplier fieldRelativeSupplier;

    public DefaultMecanumDriveCommand(MecanumDrive mecanum, DoubleSupplier xSupplier, DoubleSupplier ySupplier, 
    DoubleSupplier omega, BooleanSupplier fieldRelative) {

        this.mecanum = mecanum;
        this.translationXSupplier = xSupplier;
        this.translationYSupplier = ySupplier;
        this.omegaSupplier = omega;
        this.fieldRelativeSupplier = fieldRelative;

        addRequirements(mecanum);
    }

    @Override
    public void execute() {
        mecanum.drive(new Translation2d(translationXSupplier.getAsDouble(), 
            translationYSupplier.getAsDouble()), 
            omegaSupplier.getAsDouble(), 
            fieldRelativeSupplier.getAsBoolean()
        );
    }
}
