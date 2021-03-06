package org.firstinspires.ftc.teamcode;

class MotionProfilingController {
    Pose2d robotPose;

    double reqX = 0, reqY = 0, reqTheta = 0, reqV = 0, reqA = 0;
    double correctionX = 0, correctionY = 0, correctionTheta = 0;
    double currentX = 0, currentY = 0, currentTheta = 0;
    long timeChange, initTime;
    boolean first = true;
    boolean auton;
    public double getP;
    double maxV, maxA;

    double[] correctionVals = new double[3];
    final double xKP = 5, xKI = 0, xKD = 0, xKV = 0, xKA = 0;
    final double yKP = 5, yKI = 0, yKD = 0, yKV = 0, yKA = 0;
    final double rKP = 0, rKI = 0, rKD = 0, rKV = 0, rKA = 0;

    MotionProfile motionProfileX;
    MotionProfile motionProfileY;

    private MotionProfilePIDController pidYDistance;
    private MotionProfilePIDController pidXDistance;
    private PIDController pidTheta;

    boolean inMotion = false;


    public MotionProfilingController(Pose2d robotPose, double maxV, double maxA, boolean auton) {
        this.auton = auton;
        this.maxV = maxV;
        this.maxA = maxA;
        this.robotPose = robotPose;

        this.initTime = System.currentTimeMillis();
    }

    public void updateRequestedPose(double x, double y, double theta, double v, double a){
        this.reqX = x;
        this.reqY = y;
        this.reqTheta = theta;
        this.reqV = v;
        this.reqA = a;

        motionProfileY = new MotionProfile(this.reqY, maxV, maxA);
        pidYDistance = new MotionProfilePIDController(motionProfileY, yKP, yKI, yKD, xKV, xKA);

        motionProfileX = new MotionProfile(this.reqX, maxV, maxA);
        pidXDistance = new MotionProfilePIDController(motionProfileX, xKP, xKI, xKD, yKV, yKA);

        pidTheta = new PIDController(reqTheta, rKP, rKI, rKD, true);
    }

    public double[] updateLoop(){

        this.currentX = robotPose.getX();
        this.currentY = robotPose.getY();
        this.currentTheta = robotPose.getHeading();
        if (first) {
            timeChange = System.currentTimeMillis() - initTime;
            first = false;
        }


        if(auton){
            correctionX = pidXDistance.update(motionProfileX.getP((double) (System.currentTimeMillis() - timeChange - initTime)));

            correctionY = pidYDistance.update(motionProfileY.getP((double) (System.currentTimeMillis() - timeChange - initTime)));

            correctionTheta = pidTheta.update(currentTheta);

            correctionVals[0] = correctionX;
            correctionVals[1] = correctionY;
            correctionVals[2] = correctionTheta;
        } else {
            correctionVals[0] = 0;
            correctionVals[1] = 0;
            correctionVals[2] = 0;
        }

        return correctionVals;
    }
}