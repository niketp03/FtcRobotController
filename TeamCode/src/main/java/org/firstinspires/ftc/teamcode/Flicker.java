package org.firstinspires.ftc.teamcode;

public class Flicker {

    private StepperServo flickerServo;

    long timeOfClick = 0l;

    final float initialAngle = 0;
    final float extendedAngle = 60;

    final float delay = 20;

    public Flicker(Component servo){
        this.flickerServo = (StepperServo) servo;
        flickerServo.setAngle(initialAngle);
    }

    //Run when button pressed
    public void flick(boolean clicked){
        if (clicked){
            flickerServo.setAngle(extendedAngle);
            timeOfClick = System.currentTimeMillis();
        } else {
            if (System.currentTimeMillis() - timeOfClick >= delay){
                flickerServo.setAngle(initialAngle);
            }
        }
    }

}