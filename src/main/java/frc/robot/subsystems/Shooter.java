package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    
    
    private final TalonFX kRotationMotor = new TalonFX(0);
    
    private final PositionDutyCycle closedLoop = new PositionDutyCycle(0.0d).withEnableFOC(false);
    
    public Shooter() {
        
    }

    public void open(Solenoid solenoid) {
        solenoid.set(true);
    }

    public void close(Solenoid solenoid) {
        solenoid.set(false);
    }

    public void rotate() {
        kRotationMotor.setControl(closedLoop.withPosition(Constants.ShooterConstants.kRotation));
    }
}
