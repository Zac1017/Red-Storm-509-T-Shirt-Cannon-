// package frc.robot.subsystems;

// import static edu.wpi.first.units.Units.Amps;
// import static edu.wpi.first.units.Units.Degrees;
// import static edu.wpi.first.units.Units.DegreesPerSecond;
// import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
// import static edu.wpi.first.units.Units.Meters;
// import static edu.wpi.first.units.Units.Pounds;
// import static edu.wpi.first.units.Units.Seconds;

// import com.ctre.phoenix6.hardware.TalonFX;

// import edu.wpi.first.math.system.plant.DCMotor;
// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import yams.gearing.GearBox;
// import yams.gearing.MechanismGearing;
// import yams.mechanisms.config.PivotConfig;
// import yams.mechanisms.positional.Pivot;
// import yams.motorcontrollers.SmartMotorControllerConfig;
// import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
// import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
// import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
// import yams.motorcontrollers.remote.TalonFXWrapper;

// public class TurretYAMSTest extends SubsystemBase {
    
//     private final TalonFX kRotationMotor = new TalonFX(0);

//     private final SmartMotorControllerConfig rotationMotorConfig = new SmartMotorControllerConfig()
//         .withControlMode(ControlMode.CLOSED_LOOP)
//         .withClosedLoopController(4, 0, 0, DegreesPerSecond.of(180), DegreesPerSecondPerSecond.of(90))
//         .withGearing(new MechanismGearing(GearBox.fromReductionStages(3, 4)))
//         .withIdleMode(MotorMode.BRAKE)
//         .withMotorInverted(false)
//         .withTelemetry("TurretMotor", TelemetryVerbosity.HIGH)
//         .withStatorCurrentLimit(Amps.of(40))
//         .withClosedLoopRampRate(Seconds.of(0.25))
//         .withOpenLoopRampRate(Seconds.of(0.25));

//     private final TalonFXWrapper rotationMotorSMC = new TalonFXWrapper(kRotationMotor, DCMotor.getNEO(1), rotationMotorConfig);

//     private final PivotConfig turretConfig = new PivotConfig(rotationMotorSMC)
//         .withStartingPosition(Degrees.of(0))
//         .withWrapping(Degrees.of(0), Degrees.of(360))
//         .withHardLimit(Degrees.of(0), Degrees.of(360))
//         .withTelemetry("TurretMech", TelemetryVerbosity.HIGH)
//         .withMOI(Meters.of(0.25), Pounds.of(4));

//     private final Pivot turretPivot = new Pivot(turretConfig);
        
//     public TurretYAMSTest() {

//     }

//     public Command setPivotAngle(Angle angle) {
//         return turretPivot.setAngle(angle);
//     }
//     public Angle getAngle() {
//         return turretPivot.getAngle();
//     }

//     @Override
//     public void periodic() {
//         turretPivot.updateTelemetry();
//     }
//     @Override
//     public void simulationPeriodic() {
//         turretPivot.simIterate();
//     }
// }
