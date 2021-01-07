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

        telemetry.addData("rot", robot.currentR);
        telemetry.addData("x", robot.getOdoX());
        telemetry.addData("y", robot.getOdoY());
    }
}