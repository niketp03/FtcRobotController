package org.firstinspires.ftc.teamcode;

class MotionProfilingController {
    Pose robotPose;

    double reqX = 0, reqY = 0, reqTheta = 0, reqV = 0, reqA = 0;
    double correctionX = 0, correctionY = 0, correctionTheta = 0;
    double currentX = 0, currentY = 0, currentTheta = 0;
    long tStart;

    double maxV, maxA;

    double[] correctionVals = new double[3];
    final double xKP = 10, xKI = 0, xKD = 0, xKV = 5, xKA = 0;
    final double yKP = 10, yKI = 0, yKD = 0, yKV = 5, yKA = 0;
    final double rKP = 10, rKI = 0, rKD = 0, rKV = 5, rKA = 0;

    MotionProfile motionProfileX;
    MotionProfile motionProfileY;

    private MotionProfilePIDController pidYDistance;
    private MotionProfilePIDController pidXDistance;
    private PIDController pidTheta;

    boolean inMotion = true;


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