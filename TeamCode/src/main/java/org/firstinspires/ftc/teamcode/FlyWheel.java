package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

public class FlyWheel {

    public Motor shooter1;
    public Motor shooter2;
    
    private double targetRPM;
    
    private long lastTime;
    
    private double pastTicks;
    private double pastTicks2;

    //temporary value set here
    targetRPM = 4000.0;

    PIDController flywheelPID = new PIDController(targetRPM, rKPR, rKIR, rKDR, false);
    PIDController flywheelPID2 = new PIDController(targetRPM, rKPR, rKIR, rKDR, false);

    public double shooter1Speed = 0.0;
    public double shooter2Speed = 0.0;
    
    private double shooterFF = 0.7;

    public FlyWheel(Component shooter1, Component shooter2, boolean auton){
        this.shooter1 = (Motor) shooter1;
        this.shooter2 = (Motor) shooter2;

        shooter1Speed = 1;
        shooter2Speed = 1;
        
        lastTime = System.currentTimeMillis();
        
        pastTicks = shooter1.getEncoderValue();
        pastTicks2 = shooter2.getEncoderValue();
    }
    
    public void moveWheels(){


        shooter1.setSpeed(Range.clip(shooter1Speed, -1, 1));
        shooter2.setSpeed(Range.clip(shooter2Speed, -1, 1));

        long time = System.currentTimeMillis();
        
        //RPM calculation
        double currentTicks = shooter1.getEncoderValue();
        double currentTicks2 = shooter2.getEncoderValue();
        
        currentRPM = ((shooter1.getEncoderValue() - pastTicks) / (time - lastTime)) * ((60000 * 40)/(28*22));
        currentRPM2 = ((shooter2.getEncoderValue() - pastTicks) / (time - lastTime)) * ((60000 * 40)/(28*22));
        
        lastTime = time
        
        pastTicks = currentTicks;
        pastTicks2 = currentTicks2;

        correction1 = flywheelPID.update(currentRPM);
        correction2 = flywheelPID2.update(currentRPM2);

        shooter1Speed = correction1 + shooterFF;
        shooter2Speed = correction2 + shooterFF;
    }



    public void resetMotorSpeeds(){
        shooter1Speed = 0.7;
        shooter2Speed = 0.7;

    }

    public void resetAllEncoders(){
        shooter1.resetEncoder();
        shooter2.resetEncoder();
    }

    public void stop(){
        shooter1.setSpeed(Range.clip(0, -1, 1));
        shooter2.setSpeed(Range.clip(0, -1, 1));

    }

}
