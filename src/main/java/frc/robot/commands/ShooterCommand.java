package frc.robot.commands;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
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

    private Timer stateTimer = new Timer();


    private enum ShooterState {
        ZEROING,
        ELEVATING,
        ROTATING,
        PRESSURIZING,
        FIRING,
        FINISHED,
        FAULT
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
        elevationDone = false;
        rotationDone = false;

        if (!shooter.hasZeroedPosition) {
            setState(ShooterState.ZEROING);
        } else {
            setState(ShooterState.ELEVATING);
        }
    }

    @Override
    public void execute() {
        switch (state) {
            case ZEROING:
                if (shooter.hasZeroedPosition) {
                    setState(ShooterState.ELEVATING);
                } else if (stateTimer.hasElapsed(Constants.ShooterConstants.kZeroingTimeout)) setState(ShooterState.FAULT);
                break;
            case ELEVATING:
                if (!elevationDone) {
                    shooter.elevateShooter(elevation);
                    elevationDone = true;
                    
                }
                if (shooter.isElevated()) {
                    setState(ShooterState.ROTATING);
                }
                if (shooter.isRotated()) {
                    shooter.closeSeal();
                    setState(ShooterState.PRESSURIZING);
                }
                
                // if (shooter.isElevated() && shooter.isReadyToFire()) {
                //     state = ShooterState.ROTATING;
                // } else if (stateTimer.hasElapsed(Constants.ShooterConstants.kElevatingTimout)) setState(ShooterState.FAULT);
                break;
            case ROTATING:
                if (!rotationDone) {
                    shooter.rotateToNextBarrel(Constants.ShooterConstants.kRotation);
                    rotationDone = true;
                }

                if (shooter.isRotated() && shooter.isReadyToFire()) {
                    shooter.closeSeal();
                    state = ShooterState.PRESSURIZING;
                } else if (stateTimer.hasElapsed(Constants.ShooterConstants.kRotatingTimeout)) setState(ShooterState.FAULT);
                break;
            case PRESSURIZING:
                shooter.openChamber();
                
                if (shooter.isAtPressure(Constants.ShooterConstants.kPressureThreshold)) {
                    shooter.closeChamber();
                    state = ShooterState.FIRING;
                } else if (stateTimer.hasElapsed(Constants.ShooterConstants.kPressurizingTimeout)) setState(ShooterState.FAULT);
                break;
            case FIRING:
                shooter.open(shooter.kSealSolenoid);

                if (shooter.getPressure() < 30) {
                    state = ShooterState.FINISHED;
                } else if (stateTimer.hasElapsed(Constants.ShooterConstants.kFiringTimeout)) setState(ShooterState.FAULT);
                break;
            case FINISHED:
                break;
            case FAULT:
                shooter.closeSeal();
                shooter.closeChamber();
                shooter.stopMotors();
                break;

        }
    }

    public void setState(ShooterState newState) {
        state = newState;
        stateTimer.restart();
    }

    @Override
    public boolean isFinished() {
        return state == ShooterState.FINISHED || state == ShooterState.FAULT;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.closeSeal();
        shooter.closeChamber();
        shooter.stopMotors();
        
        if (!interrupted && state == ShooterState.FINISHED) {
            shooter.elevateShooter(Constants.ShooterConstants.kdefaultElevation);
        }
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelIncoming;
    }
    
}
