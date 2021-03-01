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

    /*
    Odometer 1 = R
    Odometer 2 = L
    Odometer 3 = M
     */

    public Pose2d robotPose = new Pose2d(
            15.3543307,
            0.1673228,
            0.6968503935
    );

    /*
    public Pose robotPose = new Pose(
            Math.PI/2, Math.PI/2, 0.0,
            7.7716535, -7.5826772, -0.0551181,
            -1.314961, -1.314961, -0.1673228,
            0.6968503935
            );
    */

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

    public float xCor = 0;
    public float yCor = 0;
    public float rCor = 0;

    public boolean auton;


    private PIDController pidYDistance = new PIDController(0f, yKPR, yKIR, yKDR, false);
    private PIDController pidXDistance = new PIDController(0f, xKPR, xKIR, xKDR, false);
    private PIDController pidRotation = new PIDController(0.0f, rKPR, rKIR, rKDR, true);

    public MotionProfilingController mpController = new MotionProfilingController(robotPose, 0.031, 0.0001);

    public Robot(HardwareMap map, boolean auton){
        this.auton = auton;
        this.components = new Component[]{
                new Motor(3, "backLeft", map, true),              //0 left odometer
                new Motor(2, "backRight", map, false),              //1 right odometer
                new Motor(1, "frontLeft", map, true),             //2 middle odometer
                new Motor(0, "frontRight", map, false),             //3
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

        robotPose.updateOdometry(new double[][]{
                {-1 * (double) ((Motor) components[1]).getEncoderValue()}, //odo 1 = R
                {(double) ((Motor) components[0]).getEncoderValue()}, //odo 2 = L
                {(double) ((Motor) components[2]).getEncoderValue()}  //odo 3 = M
        });

        currentR = gyro.getHeading();
        lastY = currentY;
        currentY = getOdoY();
        lastX = currentX;
        currentX = getOdoX();


        double[] values = mpController.updateLoop();
        xCor = (float) values[0];
        yCor = (float) values[1];
        rCor = (float) values[2];
        if (auton) {
            drivetrain.move(xCor, yCor, rCor);
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
