package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import frc.robot.Constants;

public class MecanumDrive {
    private final SparkMax frontRight = new SparkMax(0, MotorType.kBrushed);
    private final SparkMax frontLeft = new SparkMax(1, MotorType.kBrushed);
    private final SparkMax backRight = new SparkMax(2, MotorType.kBrushed);
    private final SparkMax backLeft = new SparkMax(3, MotorType.kBrushed);

    private final Translation2d frontLeftLocation = Constants.DriveConstants.Drive.kFrontLeftLocation;
    private final Translation2d frontRightLocation = Constants.DriveConstants.Drive.kFrontRightLocation;
    private final Translation2d backLeftLocation = Constants.DriveConstants.Drive.kBackLeftLocation;
    private final Translation2d backRightLocation = Constants.DriveConstants.Drive.kBackRightLocation;

    private final Pigeon2 pigeon;

    private final MecanumDriveKinematics kinematics = new MecanumDriveKinematics(frontLeftLocation, 
        frontRightLocation, 
        backLeftLocation, 
        backRightLocation
    );

    public MecanumDrive(Pigeon2 pigeon) {
        this.pigeon = pigeon;

    }

    public void drive(Translation2d translationMetersPerSecond, 
    double omega, 
    boolean fieldRelative,
    ChassisSpeeds chassisSpeeds
    ) {
        if (fieldRelative) {
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                translationMetersPerSecond.getX(),
                translationMetersPerSecond.getY(),
                omega,
                pigeon.getRotation2d()
            );
        } else {
            chassisSpeeds = new ChassisSpeeds(
                translationMetersPerSecond.getX(),
                translationMetersPerSecond.getY(),
                omega
            );
        }

        MecanumDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);

        frontLeft.set(wheelSpeeds.frontLeftMetersPerSecond);
        frontRight.set(wheelSpeeds.frontRightMetersPerSecond);
        backLeft.set(wheelSpeeds.rearLeftMetersPerSecond);
        backRight.set(wheelSpeeds.rearRightMetersPerSecond);


    }


}
