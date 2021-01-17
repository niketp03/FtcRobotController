package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

public class FlyWheel {



    public Motor shooter1;
    public Motor shooter2;


    private targetRPM = ?;

    PIDController flywheelPID = new PIDController(targetRPM, rKPR, rKIR, rKDR, false);
    PIDController flywheelPID2 = new PIDController(targetRPM, rKPR, rKIR, rKDR, false);


    public float shooter1Speed = 0, shooter2Speed = 0;


    public FlyWheel(Component shooter1, Component shooter2, boolean auton){
        this.shooter1 = (Motor) shooter1;
        this.shooter2 = (Motor) shooter2;



        shooter1Speed = AUTONCONST;
        shooter2Speed = AUTONCONST;
        private lastTime_ = System.currentTimeMillis();
        double pastTicks = shooter1.getEncoderValue();
        double pastTicks2 = shooter2.getEncoderValue();





    }
    private void move(){



        shooter1.setSpeed(Range.clip(shooter1Speed, -1, 1));
        shooter2.setSpeed(Range.clip(shooter2Speed, -1, 1));

        long time = System.currentTimeMillis();

        if ((time - LastTime) == 1000){

            double currentTicks = shooter1.getEncoderValue();
            double currentTicks2 = shooter2.getEncoderValue();

            currentRPM = (currentTicks - pastTicks)/28 * 60;
            currentRPM2 = (currentTicks2 - pastTicks2)/28 * 60;
            LastTime = time;
            pastTicks = currentTicks;
            pastTicks2 = currentTicks2;

        }


        correction1 = flywheelPID.update(currentRPM);
        correction2 = flywheelPID2.update(currentRPM2);


        shooter1Speed *= correction1;
        shooter2Speed *= correction2;






    }



    public void resetMotorSpeeds(){
        shooter1 = AUTONCONST;
        shooter2 = AUTONCONST;

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