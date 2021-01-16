package org.firstinspires.ftc.teamcode;

public class Flicker {

    private StepperServo flickerServo;

    long timeOfClick = 0l;

    final float initialAngle = 0;
    final float extendedAngle = 60;

    final float delay = 20;

    public Flicker(Component servo){
        this.flickerServo = (StepperServo) servo;
        flickerServo.setPosition(initialAngle);
    }

    //Run when button pressed
    public void flick(){
        flickerServo.setPosition(extendedAngle);
        timeOfClick = System.currentTimeMillis();
    }

    //Run in update loop robot class
    public void checkIfMoveBack() {
        if (System.currentTimeMillis() - timeOfClick >= delay){
            flickerServo.setPosition(initialAngle);
        }
    }

}