package org.firstinspires.ftc.teamcode;

public class Intake {
    public StepperServo intakeServo1;
    public StepperServo intakeServo2;
    public IntakeMotor intakeMotor1;
    public IntakeMotor intakeMotor2;

    public Intake(StepperServo intakeServo1, StepperServo intakeServo2, IntakeMotor intakeMotor1,
                  IntakeMotor intakeMotor2){
        this.intakeServo1 = intakeServo1;
        this.intakeServo2 = intakeServo2;
        this.intakeMotor1 = intakeMotor1;
        this.intakeMotor2 = intakeMotor2;
    }

    public void deploy(){
        intakeServo1.setAngle(0);
        intakeServo2.setAngle(0);
    }

    public void start(){
        intakeMotor1.set(1);
        intakeMotor2.set(1);
    }

    public void stop(){
        intakeMotor1.set(0);
        intakeMotor2.set(0);
    }

}
