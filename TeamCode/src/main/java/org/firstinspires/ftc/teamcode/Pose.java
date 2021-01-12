package org.firstinspires.ftc.teamcode;

import java.lang.Math;
import org.firstinspires.ftc.teamcode.Matrix;

public class Pose {

    /*
    R = radius of odometry wheel (in)
     */
    private double R;

    /*
    Matrix C:
    | a1, b1, c1 |
    | a2, b2, c2 |
    | a3, b3, c3 |
     */
    public double[][] C;
    public double[][] CInverse;

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
        return heading;
    }

    public double[][] deltaThetas;
    /*
    t1, t2, t3 = orientation of each odometry pod (radians)

    x1, x2, x3 = X-coordinates of each odometry pod from center (in)

    y1, y2, y3 = Y-coordinates of each odometry pod from center (in)
     */
    public Pose(double t1, double t2, double t3, double x1, double x2, double x3, double y1, double y2, double y3, double R){

        C = new double[][]{
                {Math.cos(t1), Math.sin(t1), x1 * Math.sin(t1) -  y1 * Math.cos(t1)},
                {Math.cos(t2), Math.sin(t2), x2 * Math.sin(t2) -  y2 * Math.cos(t2)},
                {Math.cos(t3), Math.sin(t3), x3 * Math.sin(t3) -  y2 * Math.cos(t3)}
        };
        CInverse = Matrix.inverse(C);

        this.R = R;
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
    private double[][] changeToRadians(double[][] matrixToChange){
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
    public void updateOdometry(double[][] encoderTicks){
        deltaThetas = changeToRadians(encoderTicks);
        double[][] soln = Matrix.multiply(CInverse, deltaThetas);

        double deltaX = soln[0][0] * R;
        double deltaY = soln[0][1] * R;
        double deltaHeading = soln[0][2] * R;

        x = x + deltaX;
        y = y + deltaY;
        heading = heading + deltaHeading;
    }
}
