package org.firstinspires.ftc.teamcode;

public class Flicker {

    private StepperServo flickerServo;

    long timeOfClick = 0l;

    final float initialAngle = 0;
    final float extendedAngle = 60;

    final float delay = 300;

    boolean firstClick = true;

    public Flicker(Component servo){
        this.flickerServo = (StepperServo) servo;
        flickerServo.setAngle(initialAngle);
    }

    //Run when button pressed
    public void flickControl(boolean clicked){
        if (clicked) {
            if (firstClick && System.currentTimeMillis() - timeOfClick >= delay){
                flickerServo.setAngle(extendedAngle);
                timeOfClick = System.currentTimeMillis();
                firstClick = false;
            }
        }

        if (System.currentTimeMillis() - timeOfClick >= (delay/2)){
            flickerServo.setAngle(initialAngle);
            firstClick = true;
        }
    }
}