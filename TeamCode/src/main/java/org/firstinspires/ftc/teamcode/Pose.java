package org.firstinspires.ftc.teamcode;

import java.lang.Math;
import org.firstinspires.ftc.teamcode.Matrix;

public class Pose {

    /*
    R = radius of odometry wheel (m)
     */
    private double R;

    /*
    Matrix C:
    | a1, b1, c1 |
    | a2, b2, c2 |
    | a3, b3, c3 |
     */
    private double[][] C;
    private double[][] CInverse;

    /*
        x,y = coordinates of the robot
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

    /*
    t1, t2, t3 = orientation of each odometry pod (radians)

    x1, x2, x3 = X-coordinates of each odometry pod from center (m)

    y1, y2, y3 = Y-coordinates of each odometry pod from center (m)
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
    dTheta1, dTheta2, dTheta3 = change in rotation of each odometry pod (radians)

    Matrix DeltaThetas:
    | dTheta1 |
    | dTheta2 |
    | dTheta3 |
     */
    public void updateOdometry(double[][] DeltaThetas){
        double[][] temp = Matrix.multiply(CInverse, DeltaThetas);
        double[][] soln = Matrix.multiply(temp, new double[][]{{R}});

        double deltaX = soln[0][0];
        double deltaY = soln[0][1];
        double deltaHeading = soln[0][2];

        x = x + deltaX;
        y = y + deltaY;
        heading = heading + deltaHeading;
    }
}
