package frc.robot.commands;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command {


    private final Shooter shooter;
    private final double elevation;
    
    private boolean rotationDone;
    private boolean elevationDone;


    private enum ShooterState {
        ELEVATING,
        ROTATING,
        PRESSURIZING,
        FIRING,
        FINISHED
    }

    private ShooterState state;

    public ShooterCommand(Shooter shooter, double elevation) {
        this.shooter = shooter;
        this.elevation = elevation;
        this.rotationDone = false;
        this.elevationDone = false;


        addRequirements(shooter);

        // addCommands(
        
        //     Commands.runOnce(() -> shooter.elevateShooter(elevation)),
        //     Commands.runOnce(() -> shooter.rotate(Constants.ShooterConstants.kRotation)),
        //     Commands.runOnce(() -> shooter.close(shooter.kSealSolenoid)),
        //     Commands.waitSeconds(2.0),
        //     Commands.runOnce(() -> shooter.open(shooter.kChargeTankSolenoid)),
        //     Commands.waitSeconds(2.0),
        //     Commands.runOnce(() -> shooter.close(shooter.kChargeTankSolenoid)),
        //     Commands.waitSeconds(1.0),
        //     Commands.runOnce(() -> shooter.open(shooter.kChamberTankSolenoid)),
        //     Commands.waitSeconds(2.0),
        //     Commands.runOnce(() -> shooter.close(shooter.kChamberTankSolenoid)),
        //     Commands.runOnce(() -> shooter.elevateShooter(Constants.ShooterConstants.kdefaultElevation))

        // );
    }

    @Override
    public void initialize() {
        state = ShooterState.ELEVATING;
        elevationDone = false;
        rotationDone = false;
        if (shooter.kSealSolenoid.get()) shooter.close(shooter.kSealSolenoid);
    }

    @Override
    public void execute() {
        switch (state) {
            case ELEVATING:
                if (!elevationDone) {
                    shooter.elevateShooter(elevation);
                    elevationDone = true;
                }

                if (shooter.isElevated()) {
                    state = ShooterState.ROTATING;
                }
                break;
            case ROTATING:
                if (!rotationDone) {
                    shooter.rotate(Constants.ShooterConstants.kRotation);
                    rotationDone = true;
                }

                if (shooter.isRotated()) {
                    shooter.close(shooter.kSealSolenoid);
                    state = ShooterState.PRESSURIZING;
                }
                break;
            case PRESSURIZING:
                shooter.openChamber();
                
                if (shooter.isAtPressure(Constants.ShooterConstants.kPressureThreshold)) {
                    shooter.closeChamber();
                    state = ShooterState.FIRING;
                }
                break;
            case FIRING:
                shooter.open(shooter.kSealSolenoid);

                if (shooter.getPressure() < 30) {
                    state = ShooterState.FINISHED;
                }
                break;
            case FINISHED:
                break;

        }
    }

    @Override
    public boolean isFinished() {
        return state == ShooterState.FINISHED;
    }

    
}
