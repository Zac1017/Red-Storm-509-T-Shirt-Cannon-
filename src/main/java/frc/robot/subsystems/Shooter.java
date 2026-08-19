// package frc.robot.subsystems;

// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.PositionDutyCycle;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;

// import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class Shooter extends SubsystemBase {
    
    
//     private final TalonFX kRotationMotor = new TalonFX(0);
    
//     private final PositionDutyCycle closedLoop = new PositionDutyCycle(0.0d).withEnableFOC(false);
    
//     public Shooter() {
        
//         TalonFXConfiguration driverConfig = new TalonFXConfiguration();

//         driverConfig.CurrentLimits.SupplyCurrentLimit = 40;
//         driverConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        
//         driverConfig.Slot0.kP = Constants.PIDConstants.Drive.kDriveP;
//         driverConfig.Slot0.kI = Constants.PIDConstants.Drive.kDriveI;
//         driverConfig.Slot0.kD = Constants.PIDConstants.Drive.kDriveD;
        
//         driverConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
//         driverConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

//         kRotationMotor.getConfigurator().apply(driverConfig);

//     }

//     public void open(Solenoid solenoid) {
//         solenoid.set(true);
//     }

//     public void close(Solenoid solenoid) {
//         solenoid.set(false);
//     }

//     public void rotate() {
//         kRotationMotor.setControl(closedLoop.withPosition(Constants.ShooterConstants.kRotation));
//     }
// }
