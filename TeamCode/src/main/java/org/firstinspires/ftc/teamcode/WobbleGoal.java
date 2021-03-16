package org.firstinspires.ftc.teamcode;

public class WobbleGoal {
    public StepperServo wgClaw;
    public StepperServo wgArm;

    public WobbleGoal(StepperServo wgClaw, StepperServo wgArm){
        this.wgClaw = wgClaw;
        this.wgArm = wgArm;
    }

    public void armRaise(){
        wgArm.setAngle(0);
    }

    public void armLower(){
        wgArm.setAngle(30);
    }

    public void clawClose(){
        wgClaw.setAngle(0);
    }

    public void clawOpen(){
        wgClaw.setAngle(30);
    }
}
