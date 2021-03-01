package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;


@TeleOp(name="AutonTest", group="Auton Opmode")
public class AutonTest extends OpMode {

    Robot robot;
    boolean first = true;
    
    @Override
    public void init() {
        robot = new Robot(hardwareMap, true);
        robot.mpController.updateRequestedPose(24, 0, 0, 0, 0);
    }

    @Override
    public void init_loop(){

    }

    @Override
    public void loop() {
        robot.updateLoop();

        telemetry.addData("xCor", robot.xCor);
        telemetry.addData("yCor", robot.yCor);
        telemetry.addData("rCor", robot.rCor);
        telemetry.addData("tDelta", (System.currentTimeMillis() - robot.mpController.tStart));
        telemetry.addData("getVX", (robot.mpController.motionProfileX.getV(System.currentTimeMillis() - robot.mpController.tStart)));
        telemetry.addData("getVY", (robot.mpController.motionProfileY.getV(System.currentTimeMillis() - robot.mpController.tStart)));
        
        /* --Telemetry--
        telemetry.addData("stopped", robot.stopped(true));
        telemetry.addData("PositionY", robot.currentY);
        telemetry.addData("PositionTargetY", robot.targetY);
        telemetry.addData("CorrectionY", robot.correctionY);
        telemetry.addData("highestY", robot.highestY);
        telemetry.addData("PositionX", robot.currentX);
        telemetry.addData("PositionTargetX", robot.targetX);
        telemetry.addData("CorrectionX", robot.correctionX);
        telemetry.addData("highestX", robot.highestX);
        telemetry.addData("PositionTargetX", robot.targetX);
        telemetry.addData("Rotation", robot.currentR);
        telemetry.addData("RotationTarget", robot.targetR);
        telemetry.addData("CorrectionR", robot.correctionR);
         */
    }

}
