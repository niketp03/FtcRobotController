package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Main TeleOp", group="Juice TeleOp")
public class Main extends OpMode{

    Robot robot;

    /*
    GAMEPAD CONTROLS:
    GAMEPAD 1:
        left stick x = drive horizontal
        left stick y = drive vertical
        right stick x = rotation
        left trigger =
        right trigger =
        left bumper =
        right bumper =
        a =
        b =
        x
        y
        dpad
     */

    @Override
    public void init() {
        robot = new Robot(hardwareMap, false);
        telemetry.addData("Init", "Robot created");
    }

    public void start(){
        //robot.lift.liftMotor2.resetEncoder();
        //robot.fakeMotor.resetEncoder();
    }

    @Override
    public void loop() {
        robot.updateLoop();

        if(gamepad1.dpad_down){
            robot.drive(0, 0.4f, 0f);
        }else if(gamepad1.dpad_up){
            robot.drive(0, -0.4f, 0f);
        } else if (gamepad1.dpad_right) {
            robot.drive(0.4f, 0f, 0f);
        } else if (gamepad1.dpad_left) {
            robot.drive(-0.4f, 0f, 0f);
        } else {
            robot.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, -1*gamepad1.right_stick_x);
        }

        telemetry.addData("rot", robot.robotPose.getHeading());
        telemetry.addData("x", robot.robotPose.getX());
        telemetry.addData("y", robot.robotPose.getY());

        telemetry.addData("Middle Odometer", robot.drivetrain.frontLeft.getEncoderValue());
        telemetry.addData("Left Odometer", robot.drivetrain.backLeft.getEncoderValue());
        telemetry.addData("Right Odometer", robot.drivetrain.backRight.getEncoderValue());

        //telemetry.addData("leftmemer", (robot.robotPose.C[0][0] + " " + robot.robotPose.C[0][1] + " " + robot.robotPose.C[0][2] + " " + robot.robotPose.C[1][0] + " " + robot.robotPose.C[1][1] + " " + robot.robotPose.C[1][2] + " " + robot.robotPose.C[2][0] + " " + robot.robotPose.C[2][1] + " " + robot.robotPose.C[2][2]));
        //telemetry.addData("leftmemer", (robot.robotPose.CInverse[0][0] + " " + robot.robotPose.CInverse[0][1] + " " + robot.robotPose.CInverse[0][2] + " " + robot.robotPose.CInverse[1][0] + " " + robot.robotPose.CInverse[1][1] + " " + robot.robotPose.CInverse[1][2] + " " + robot.robotPose.CInverse[2][0] + " " + robot.robotPose.CInverse[2][1] + " " + robot.robotPose.CInverse[2][2]));
        //telemetry.addData("leftmemer", (robot.robotPose.deltaThetas[1][0] + " " + robot.robotPose.deltaThetas[1][0] + " " + robot.robotPose.deltaThetas[2][0]));
        //telemetry.addData("leftmemer", (robot.robotPose.encoderTicks[0][0] + " " + robot.robotPose.encoderTicks[1][0] + " " + robot.robotPose.encoderTicks[2][0]));
    }
}