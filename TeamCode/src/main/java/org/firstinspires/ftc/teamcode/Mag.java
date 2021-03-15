package org.firstinspires.ftc.teamcode;

class Mag {
    long clickTime = 0l;

    private StepperServo leftMag;
    private StepperServo rightMag;
    private StepperServo midMag;

    final float initialAngleLeft = 0;
    final float raisedAngleLeft = 60;
    final float initialAngleRight = 0;
    final float raisedAngleRight = 60;
    final float flat = 0;
    final float tilted = 20;

    final float delay = 200;

    boolean firstClick = true;

    public Mag(Component leftMag, Component midMag, Component rightMag){
        this.leftMag = (StepperServo) leftMag;
        this.rightMag = (StepperServo) rightMag;
        this.midMag = (StepperServo) midMag;
        this.leftMag.setAngle(initialAngleLeft);
        this.rightMag.setAngle(initialAngleRight);
        this.midMag.setAngle(flat);
    }

    public void raiseControl(boolean clicked){
        if (clicked) {
            leftMag.setAngle(raisedAngleLeft);
            rightMag.setAngle(raisedAngleRight);
            if (firstClick) {
                clickTime = System.currentTimeMillis();
                firstClick = false;
            }
        }

        if (System.currentTimeMillis() - clickTime >= delay){
            midMag.setAngle(tilted);
            firstClick = true;
        }
    }

    public void lowerControl(boolean clicked){
        if (clicked) {
            leftMag.setAngle(initialAngleLeft);
            rightMag.setAngle(initialAngleRight);
            if (firstClick) {
                clickTime = System.currentTimeMillis();
                firstClick = false;
            }
        }

        if (System.currentTimeMillis() - clickTime >= delay){
            midMag.setAngle(flat);
            firstClick = true;
        }
    }
}
