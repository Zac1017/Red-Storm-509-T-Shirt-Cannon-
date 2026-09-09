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
        
        TalonFXConfiguration driverConfig = new TalonFXConfiguration();

        driverConfig.CurrentLimits.SupplyCurrentLimit = 40;
        driverConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        
        driverConfig.Slot0.kP = Constants.PIDConstants.Drive.kDriveP;
        driverConfig.Slot0.kI = Constants.PIDConstants.Drive.kDriveI;
        driverConfig.Slot0.kD = Constants.PIDConstants.Drive.kDriveD;
        
        driverConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        driverConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        kRotationMotor.getConfigurator().apply(driverConfig);

    }

    public void open(Solenoid solenoid) {
        solenoid.set(true);
    }

    public void close(Solenoid solenoid) {
        solenoid.set(false);
    }

    public void push(Solenoid solenoid) {
        solenoid.set(true);
    }

    public void rotate(double angle) {
        kRotationMotor.setControl(closedLoop.withPosition((angle / 360.0d) * Constants.ShooterConstants.kGearRatio));
    }

    public void elevateShooter(double elevation){
        kElevationMotor.setControl(closedLoop.withPosition((elevation / 360.0d) * Constants.ShooterConstants.kGearRatio));
    }
}
