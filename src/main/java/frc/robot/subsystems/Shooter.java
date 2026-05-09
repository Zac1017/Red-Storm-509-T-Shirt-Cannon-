package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    private DoubleSolenoid solenoid = new DoubleSolenoid(0, 
        PneumaticsModuleType.CTREPCM, 
        0, 
        0
    );
    
    public Shooter() {
        
    }

    public void shoot() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void reverse() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void stop() {
        solenoid.set(DoubleSolenoid.Value.kOff);
    }
}
