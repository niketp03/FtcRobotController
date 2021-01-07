package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.vision.SkystoneDetector;

import org.firstinspires.ftc.teamcode.archive.SkystoneDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@TeleOp(name="Main TeleOp", group="Juice TeleOp")
public class Main extends OpMode{

    Robot robot;
    OpenCvCamera camera;
    SkystoneDetector pipeline;

    /*
    GAMEPAD CONTROLS:
    GAMEPAD 1:
        left stick x = drive horizontal
        left stick y = drive vertical
        right stick x = rotation
        left trigger = lift down
        right trigger = lift up
        left bumper = slow speed
        right bumper = turbo speed
        a = intake claw
        b = foundation
        x
        y
        dpad
     */

    @Override
    public void init() {
        robot = new Robot(hardwareMap, false);

        telemetry.addData("Test", "Robot");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        camera.openCameraDevice();

        pipeline = new SkystoneDetector(25, 25, 50, 50, null);

        camera.setPipeline(pipeline);
        camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);

    }

    public void start(){
        robot.lift.liftMotor2.resetEncoder();
        robot.fakeMotor.resetEncoder();
    }

    @Override
    public void loop() {
        robot.turbo(gamepad1.right_bumper);

        robot.intakeControl(gamepad2.a || gamepad1.a);

        robot.huggerControl(gamepad2.x, gamepad2.b);

        robot.actuatorControl(gamepad2.dpad_up, gamepad2.dpad_down);

        robot.switchHuggers(gamepad2.dpad_left, gamepad2.dpad_right);

        robot.moveLift(gamepad2.left_trigger, gamepad2.right_trigger);

        robot.foundationHookControl(gamepad2.y);

        if(gamepad2.left_bumper){
            robot.moveLift(1,0);
            robot.actuatorControl(true,false);
        }

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

        if(gamepad1.right_stick_x != 0.0f){
            //robot.changeTarget(0,0);
        }

        telemetry.addData("rot", robot.currentR);
        telemetry.addData("limit", robot.limit.digitalTouch.isPressed());
        telemetry.addData("x", robot.getOdoX());
        telemetry.addData("y", robot.getOdoY());
        telemetry.addData("Detection", pipeline.getSkystonePosition());
        //telemetry.addData("y", robot.limit.digitalTouch.isPressed());
    }
}