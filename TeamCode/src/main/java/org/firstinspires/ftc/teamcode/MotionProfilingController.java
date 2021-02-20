package org.firstinspires.ftc.teamcode;

class MotionProfilingController {
    Pose robotPose;

    double reqX = 0, reqY = 0, reqTheta = 0, reqV = 0, reqA = 0;
    double correctionX = 0, correctionY = 0, correctionTheta = 0;
    double currentX = 0, currentY = 0, currentTheta = 0;
    long tStart;

    double maxV, maxA;

    double[] correctionVals = new double[3];
    final double xKP = 1, xKI = 1, xKD = 1, xKV = 1, xKA = 1;
    final double yKP = 1, yKI = 1, yKD = 1, yKV = 1, yKA = 1;
    final double rKP = 1, rKI = 1, rKD = 1, rKV = 1, rKA = 1;

    MotionProfile motionProfileX;
    MotionProfile motionProfileY;

    private MotionProfilePIDController pidYDistance;
    private MotionProfilePIDController pidXDistance;
    private PIDController pidTheta;

    boolean inMotion = false;


    public MotionProfilingController(Pose robotPose, double maxV, double maxA) {
        this.maxV = maxV;
        this.maxA = maxA;
        this.robotPose = robotPose;
        this.tStart = System.currentTimeMillis();
    }

    public void updateRequestedPose(double x, double y, double theta, double v, double a){
        this.reqX = x;
        this.reqY = y;
        this.reqTheta = theta;
        this.reqV = v;
        this.reqA = a;

        motionProfileY = new MotionProfile(this.reqY, maxV, maxA);
        MotionProfilePIDController pidYDistance = new MotionProfilePIDController(motionProfileY, yKP, yKI, yKD, xKV, xKA);

        motionProfileX = new MotionProfile(this.reqX, maxV, maxA);
        MotionProfilePIDController pidXDistance = new MotionProfilePIDController(motionProfileX, xKP, xKI, xKD, yKV, yKA);

        pidTheta = new PIDController(reqTheta, rKP, rKI, rKD, true);
    }

    public double[] updateLoop(){

        this.currentX = robotPose.getX();
        this.currentY = robotPose.getY();
        this.currentTheta = robotPose.getHeading();

        if(inMotion){
            correctionX = pidXDistance.update(motionProfileX.getP(System.currentTimeMillis() - tStart));

            correctionY = pidYDistance.update(motionProfileY.getP(System.currentTimeMillis() - tStart));

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