package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    
    
    private final TalonFX kRotationMotor = new TalonFX(0);

    private final TalonFX kElevationMotor = new TalonFX(0);
                                            // new Solenoid(module, PneumaticsModuleType, channel);
    public final Solenoid kChargeTankSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);

    public final Solenoid kChamberTankSolenoid = new Solenoid(1, PneumaticsModuleType.CTREPCM, 0);
    
    public final Solenoid kSealSolenoid = new Solenoid(3, PneumaticsModuleType.CTREPCM, 0);

    //two open close (chambers)
    //two push (seal)

    private final PositionDutyCycle closedLoop = new PositionDutyCycle(0.0d).withEnableFOC(false);
    
    public Shooter() {
        
        TalonFXConfiguration rotationMotorConfig = new TalonFXConfiguration();
        
        rotationMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        rotationMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        
        rotationMotorConfig.Slot0.kP = Constants.DriveConstants.PIDConstants.Drive.kDriveP;
        rotationMotorConfig.Slot0.kI = Constants.DriveConstants.PIDConstants.Drive.kDriveI;
        rotationMotorConfig.Slot0.kD = Constants.DriveConstants.PIDConstants.Drive.kDriveD;
        
        rotationMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        rotationMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        
        TalonFXConfiguration elevationMotorConfig = new TalonFXConfiguration(); 

        elevationMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        elevationMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        elevationMotorConfig.Slot0.kP = Constants.DriveConstants.PIDConstants.Drive.kDriveP;
        elevationMotorConfig.Slot0.kI = Constants.DriveConstants.PIDConstants.Drive.kDriveI;
        elevationMotorConfig.Slot0.kD = Constants.DriveConstants.PIDConstants.Drive.kDriveD;

        elevationMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        elevationMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        
        kRotationMotor.getConfigurator().apply(rotationMotorConfig);
        kElevationMotor.getConfigurator().apply(elevationMotorConfig);

    }

    public void open(Solenoid solenoid) {
        solenoid.set(true);
    }

    public void close(Solenoid solenoid) {
        solenoid.set(false);
    }

    public void rotate(double angle) {
        kRotationMotor.setControl(closedLoop.withPosition((angle / 360.0d) * Constants.ShooterConstants.kGearRatio));
    }

    public void elevateShooter(double elevation){
        kElevationMotor.setControl(closedLoop.withPosition((elevation / 360.0d) * Constants.ShooterConstants.kGearRatio));
    }
}
