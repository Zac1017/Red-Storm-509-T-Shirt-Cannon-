// package frc.robot.subsystems;

// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.VelocityDutyCycle;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;

// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class TankDrive extends SubsystemBase {

//     private final TalonFX frontRight = new TalonFX(0);
//     private final TalonFX frontLeft = new TalonFX(0);
//     private final TalonFX backRight = new TalonFX(0);
//     private final TalonFX backLeft = new TalonFX(0);

//     private final VelocityDutyCycle closedLoop = new VelocityDutyCycle(0);

    
//     public TankDrive(){

//         TalonFXConfiguration leftWheelConfigs = new TalonFXConfiguration();
        
//             leftWheelConfigs.CurrentLimits.SupplyCurrentLimit = 40;
//             leftWheelConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;

//             leftWheelConfigs.Slot0.kP = Constants.PIDConstants.Drive.kDriveP;
//             leftWheelConfigs.Slot0.kI = Constants.PIDConstants.Drive.kDriveI;
//             leftWheelConfigs.Slot0.kD = Constants.PIDConstants.Drive.kDriveD;

//             leftWheelConfigs.Slot0.kS = Constants.SVAConstants.Drive.kDriveS;
//             leftWheelConfigs.Slot0.kV = Constants.SVAConstants.Drive.kDriveV;
//             leftWheelConfigs.Slot0.kA = Constants.SVAConstants.Drive.kDriveA;

//             leftWheelConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
//             leftWheelConfigs.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive; //Check if Correct

//         TalonFXConfiguration rightWheelConfigs = new TalonFXConfiguration();

//             rightWheelConfigs.CurrentLimits.SupplyCurrentLimit = 40;
//             rightWheelConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;

//             rightWheelConfigs.Slot0.kP = Constants.PIDConstants.Drive.kDriveP;
//             rightWheelConfigs.Slot0.kI = Constants.PIDConstants.Drive.kDriveI;
//             rightWheelConfigs.Slot0.kD = Constants.PIDConstants.Drive.kDriveD;

//             rightWheelConfigs.Slot0.kS = Constants.SVAConstants.Drive.kDriveS;
//             rightWheelConfigs.Slot0.kV = Constants.SVAConstants.Drive.kDriveV;
//             rightWheelConfigs.Slot0.kA = Constants.SVAConstants.Drive.kDriveA;

//             rightWheelConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
//             rightWheelConfigs.MotorOutput.Inverted = InvertedValue.Clockwise_Positive; //Check if Correct
//     }

//     public void driveLeft(double speed) {
//         frontLeft.setControl(closedLoop.withVelocity(speed));
//         backLeft.setControl(closedLoop.withVelocity(speed));
//     }

//     public void driveRight(double speed) {
//         frontRight.setControl(closedLoop.withVelocity(speed));
//         backRight.setControl(closedLoop.withVelocity(speed));
//     }

//     public void drive(double forward, double right) {

//         forward = MathUtil.clamp(forward, -1.0, 1.0);
//         right = MathUtil.clamp(right, -1.0, 1.0);

//         driveLeft(forward + right);
//         driveRight(forward - right);
        
//     }

// }
