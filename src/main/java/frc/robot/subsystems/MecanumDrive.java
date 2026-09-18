package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class MecanumDrive {
    private final SparkMax frontRight = new SparkMax(0, MotorType.kBrushed);
    private final SparkMax frontLeft = new SparkMax(1, MotorType.kBrushed);
    private final SparkMax backRight = new SparkMax(2, MotorType.kBrushed);
    private final SparkMax backLeft = new SparkMax(3, MotorType.kBrushed);

    private final Translation2d translation2dMetersPerSecond;
    private final Rotation2d omega;
    private final DoubleSupplier isFieldRelative;

    public MecanumDrive(Translation2d translation2dMetersPerSecond, Rotation2d omega, DoubleSupplier isFieldRelative) {
        this.translation2dMetersPerSecond = translation2dMetersPerSecond;
        this.omega = omega;
        this.isFieldRelative = isFieldRelative;
    }


}
