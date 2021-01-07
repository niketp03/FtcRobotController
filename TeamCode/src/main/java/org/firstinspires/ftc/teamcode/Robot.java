package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

public class Robot {
    private Component[] components;
    public Mecanum drivetrain;
    public Gyro gyro;

    //Autonomous Constants
    public float currentR = 0.0f;
    public float targetR = 0.0f;

    public float currentY = 0.0f;
    public float targetY = 0.0f;
    public float highestY = 0.0f;

    public float currentX = 0.0f;
    public float targetX = 0.0f;
    public float highestX = 0.0f;

    public float correctionX = 0.0f;
    public float correctionY = 0.0f;
    public float correctionR = 0.0f;

    public boolean pid = true;

    private final float rKPR = 0.012f;
    private final float rKIR = 0.000005f;
    private final float rKDR = 0.0000f;

    private final float yBASE = 0.5f;
    private final float xBASE = 0.06f;

    private final float yKPR = 0.06f;
    private final float yKIR = 0.000002f;
    private final float yKDR = 0.00f;

    private final float xKPR = 0.17f;
    private final float xKIR = 0.000007f;
    private final float xKDR = 0.00f;

    private float lastX = 0;
    private float lastY = 0;



    private PIDController pidYDistance = new PIDController(0f, yKPR, yKIR, yKDR, false);
    private PIDController pidXDistance = new PIDController(0f, xKPR, xKIR, xKDR, false);
    private PIDController pidRotation = new PIDController(0.0f, rKPR, rKIR, rKDR, true);

    public Robot(HardwareMap map, boolean auton){
        this.components = new Component[]{
                new Motor(0, "backLeft", map, false),              //0
                new Motor(1, "backRight", map, true),              //1
                new Motor(2, "frontLeft", map, false),             //2
                new Motor(3, "frontRight", map, true),             //3
        };

        if (auton){
            drivetrain = new Mecanum(
                    components[0],
                    components[1],
                    components[2],
                    components[3],
                    true
            );
        } else {
            drivetrain = new Mecanum(
                    components[0],
                    components[1],
                    components[2],
                    components[3],
                    false
            );
        }

        this.gyro = new Gyro(map);

        drivetrain.resetAllEncoders();

        currentR = gyro.getHeading();
        targetR = currentR;

        //lift.liftMotor2.resetEncoder();
        //fakeMotor.resetEncoder();

        currentY = getOdoY();
        currentX = getOdoX();

        pidXDistance = new PIDController(0, xKPR, xKIR, xKDR, false);
        pidYDistance = new PIDController(0, yKPR, yKIR, yKDR, false);
        pidRotation = new PIDController(0, rKPR, rKIR, rKDR, true);

    }

    public void updateLoop(){
        currentR = gyro.getHeading();
        lastY = currentY;
        currentY = getOdoY();
        lastX = currentX;
        currentX = getOdoX();


        if(pid){
            autonMove();
        }
    }

    public void resetMotorSpeeds(){
        drivetrain.resetMotorSpeeds();
    }

    public void stop() {
        drivetrain.stop();
    }

    public void turbo(boolean turbo){
        drivetrain.setTurbo(turbo);
    }

    public void drive(float xMove, float yMove, float rotate) {
        drivetrain.move(xMove, yMove, rotate);
    }


    public float getOdoX(){
        return 0.0f;
        //return (fakeMotor.getEncoderValue() / (8192f)) * 6.1842375f;
    }

    public float getOdoY(){
        return 0.0f;
        //return (lift.liftMotor2.getEncoderValue() / (8192f)) * 6.1842375f;
    }

    public void changeTarget(float x, float y, float r){
        //check if targetX has changed
        if(x != targetX){
            //float xKP = xKPR;
            /*if(Math.abs(targetX) <= 10){
                xKP = xKPR_SMALL;
                counterBadX++;
            }

             */
            //fakeMotor.resetEncoder();
            //targetX = x;
            //pidXDistance = new PIDController(targetX, xKP, xKIR, xKDR, false);

            //fakeMotor.resetEncoder(); //reset x odometry encoder
        }

        //check if targetY has changed
        /*if(y != targetY){

            lift.liftMotor2.resetEncoder();
            targetY = y;
            pidYDistance = new PIDController(targetY, yKPR, yKIR, yKDR, false);
            counterBadY++;
            if(targetY!=10.0){
                rlyBad = true;
            }
            //lift.liftMotor2.resetEncoder();//reset y odometry encoder
        }

        if(r != this.targetR){
            targetR = r;
            pidRotation = new PIDController(targetR, rKPR, rKIR, rKDR, true);
        }
        */
    }

    public void autonMove(){
        /*correctionX = pidXDistance.update(currentX);
        correctionY = pidYDistance.update(currentY);
        correctionR = pidRotation.update(currentR);

        float xSpeed = -Range.clip(correctionX, -1, 1);
        float ySpeed = Range.clip(correctionY, -1, 1);
        float rSpeed = correctionR;

        if(autonRotating){
            xSpeed = 0;
            ySpeed = 0;
        }

        drivetrain.autonMove(xSpeed, ySpeed, rSpeed);*/
    }

    public static boolean tol(float current, float target, float tolerance){
        return Math.abs(current - target) <= tolerance;
    }

    public boolean stopped(boolean x){
        if(x){
            return Math.abs(lastX - currentX) <= 0.01;
        } else {
            return Math.abs(lastY - currentY) <= 0.01;
        }
    }
}
