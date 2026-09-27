package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    
    
    private final TalonFX kRotationMotor = new TalonFX(4);
    private final TalonFX kElevationMotor = new TalonFX(5);   

    public final Solenoid kLeftChamberTankSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0); // new Solenoid(module, PneumaticsModuleType, channel);
    public final Solenoid kRightChamberTankSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 1);
    public final Solenoid kSealSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 2);

    public boolean hasZeroedPosition = false;
    public double zeroPosition;
    public double targetElevation;
    public double globalRotation;
    
    //two open close (chambers)
    //two push (seal)

    private final PositionDutyCycle closedLoop = new PositionDutyCycle(0.0d).withEnableFOC(false);
    private final VoltageOut openLoop = new VoltageOut(0.0d);
    
    public Shooter() {
        
        TalonFXConfiguration rotationMotorConfig = new TalonFXConfiguration();
        
        rotationMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        rotationMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        
        // rotationMotorConfig.Slot0.kP = Constants.DriveConstants.PIDConstants.Drive.kDriveP;
        // rotationMotorConfig.Slot0.kI = Constants.DriveConstants.PIDConstants.Drive.kDriveI;
        // rotationMotorConfig.Slot0.kD = Constants.DriveConstants.PIDConstants.Drive.kDriveD;
        
        rotationMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        rotationMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        
        TalonFXConfiguration elevationMotorConfig = new TalonFXConfiguration(); 

        elevationMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        elevationMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        // elevationMotorConfig.Slot0.kP = Constants.DriveConstants.PIDConstants.Drive.kDriveP;
        // elevationMotorConfig.Slot0.kI = Constants.DriveConstants.PIDConstants.Drive.kDriveI;
        // elevationMotorConfig.Slot0.kD = Constants.DriveConstants.PIDConstants.Drive.kDriveD;

        elevationMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        elevationMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        
        kRotationMotor.getConfigurator().apply(rotationMotorConfig);
        kElevationMotor.getConfigurator().apply(elevationMotorConfig);

    }

    /**
     * Opens Solenoid
     * @param solenoid
     */
    public void open(Solenoid solenoid) {
        solenoid.set(true);
    }

    /**
     * Closes Solenoid
     * @param solenoid
     */
    public void close(Solenoid solenoid) {
        solenoid.set(false);
    }

    /**
     * Rotates barrel "angle" amount of degrees
     * @param angle
     */
    public void rotate(double angle) {
        kRotationMotor.setControl(closedLoop.withPosition(((globalRotation += angle) / 360.0d) * Constants.ShooterConstants.kGearRatio));
    }

    /**
     * Elevates shooter to "elevation" height in meters
     * @param elevation
     */
    public void elevateShooter(double elevation){
        targetElevation = elevation;

        kElevationMotor.setControl(closedLoop.withPosition((elevation / 360.0d) * Constants.ShooterConstants.kGearRatio));
    }
    
    /**
     * Zeroing the barrel
     */
    public void zeroBarrel() {
        if (!hasZeroedPosition) {
            double current = Math.abs(kRotationMotor.getTorqueCurrent().getValueAsDouble());

            if (current > Constants.ShooterConstants.zeroeCurrentThreshold) {
                kRotationMotor.setControl(openLoop.withOutput(0));
                kRotationMotor.setPosition(0);
                globalRotation = 0;
                // zeroPosition = kRotationMotor.getPosition().getValueAsDouble();
                hasZeroedPosition = true;
                globalRotation = 0;
            } else {
                kRotationMotor.setControl(openLoop.withOutput(Constants.ShooterConstants.zeroVoltage));
            }

        }
    }

    // public boolean isAtPressure(double pressureThreshold) {
    //     // double pressure = pressureSensor.getPressure(); 
    //     // return pressure >= pressureThreshold;
    // }

    // public double getPressure() {
    //     return pressureSensor.getPressure();
    // }

    public boolean isRotated() {
        double currentRotation = kRotationMotor.getPosition().getValueAsDouble();

        double targetRotation = (globalRotation / 360.0d) * Constants.ShooterConstants.kGearRatio;

        return Math.abs(currentRotation - targetRotation) < 0.02;
    }

    public boolean isElevated() {
        double currentPosition = kElevationMotor.getPosition().getValueAsDouble();

        double targetPosition = (targetElevation / 360.0d) * Constants.ShooterConstants.kGearRatio;

        return Math.abs(currentPosition - targetPosition) < 0.02; // 0.02 for tolerance
    }

    public void openChamber() {
        open(kLeftChamberTankSolenoid);
        open(kRightChamberTankSolenoid);
    }

    public void closeChamber() {
        close(kLeftChamberTankSolenoid);
        close(kRightChamberTankSolenoid);
    }

    @Override
    public void periodic() {
        zeroBarrel();
    }
}

