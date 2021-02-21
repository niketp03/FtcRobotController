package org.firstinspires.ftc.teamcode;

public class Pose2d {

    /*
    R = radius of odometry wheel (in)
     */
    private double R;

    private double x_0;
    private double y_0;

    /*
        x,y = coordinates of the robot (in)
        heading = current direction of the robot (degrees)
            0 is forward
            90 is right
            -90 is left
         */
    private double x = 0.0;
    private double y = 0.0;
    private double heading = 0.0;



    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return (heading * 180) / Math.PI;
    }

    public double[][] deltaThetas;

    public double[][] encoderTicks = new double[][]{
            {0},
            {0},
            {0}
    };
    public double[][] deltaEncoderTicks = new double[][]{
            {0},
            {0},
            {0}
    };
    public double[][] oldTicks = new double[][]{
            {0},
            {0},
            {0}
    };

    /*
    t1, t2, t3 = orientation of each odometry pod (radians)

    x1, x2, x3 = X-coordinates of each odometry pod from center (in)

    y1, y2, y3 = Y-coordinates of each odometry pod from center (in)
     */
    public Pose2d(double x_0, double y_0, double R){
        this.R = R;

        this.x_0 = x_0;
        this.y_0 = y_0;
    }

    /*
    Changes an input value (encoder ticks) to a radian output
     */
    private double encoderToRad(double encVal){
        return (encVal/8192) * 2 * Math.PI;
    }

    /*
    Changes a matrix of encoder ticks to a matrix of radians
     */
    private double[][] changeToRadians(double[][] matrixTemp){
        double[][] matrixToChange = matrixTemp;

        for (int i = 0; i < matrixToChange.length; i++){
            for (int j = 0; j < matrixToChange[i].length; j++){
                matrixToChange[i][j] = encoderToRad(matrixToChange[i][j]);
            }
        }
        return matrixToChange;
    }

    /*
    dTheta1, dTheta2, dTheta3 = change in rotation of each odometry pod (encoder ticks)

    Matrix deltaThetas:
    | dTheta1 |
    | dTheta2 |
    | dTheta3 |
     */
    public void updateOdometry(double[][] encoderTicks) {

        this.encoderTicks = encoderTicks;

        this.deltaEncoderTicks[0][0] = this.encoderTicks[0][0] - this.oldTicks[0][0]; //R 0 1
        this.deltaEncoderTicks[1][0] = this.encoderTicks[1][0] - this.oldTicks[1][0]; //L 1 2
        this.deltaEncoderTicks[2][0] = this.encoderTicks[2][0] - this.oldTicks[2][0]; //M 2 3

        deltaThetas = changeToRadians(this.deltaEncoderTicks);

        double deltaX = (R / 2) * (deltaThetas[0][0] + deltaThetas[1][0]);
        double deltaY = (R) * (((x_0/(2*y_0))*(deltaThetas[0][0] - deltaThetas[1][0])) + deltaThetas[2][0]);
        double deltaHeading = (R / (2 * y_0)) * (deltaThetas[1][0] - deltaThetas[0][0]);


        x = x + deltaX;
        y = y + deltaY;
        heading = heading + deltaHeading;
        this.oldTicks = encoderTicks;
    }
}
