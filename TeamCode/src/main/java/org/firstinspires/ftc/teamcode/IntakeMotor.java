package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeMotor {
    public Motor intakeMotor;

    public IntakeMotor(Component intakeMotor){
        this.intakeMotor = (Motor) intakeMotor;
    }

    public void up(float speed){
        intakeMotor.setSpeed(speed);
    }

    public void down(float speed){
        intakeMotor.setSpeed(speed * -1);
    }

}


