package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.customUtil.ActiveLocation;
import org.firstinspires.ftc.teamcode.customUtil.Constants;
import org.firstinspires.ftc.teamcode.customUtil.RobotHardwareMap;


@TeleOp(name = "Field Oriented TeleOp", group = "TeleOps")
public class FieldOrientedTeleOp extends LinearOpMode {

    RobotHardwareMap robot = new RobotHardwareMap();


    @Override
    public void runOpMode() {
        robot.initialize(hardwareMap);

        double drive;
        double strafe;
        double turn;

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        ActiveLocation activeLocation = new ActiveLocation(robot);

        double currentAngle;

        double maxMotorSpeed = 0.7;

        double maxPower;

        int heightDifferential = 0;

        waitForStart();

        while (opModeIsActive()) {
            currentAngle = activeLocation.getAngleInRadians();

            drive = -gamepad1.left_stick_y * Math.cos(currentAngle) +
                    gamepad1.left_stick_x * Math.sin(currentAngle);
            strafe = gamepad1.left_stick_x * Math.cos(currentAngle) -
                    -gamepad1.left_stick_y * Math.sin(currentAngle);
            turn = gamepad1.right_stick_x;

            frontLeftPower = drive + strafe + turn;
            frontRightPower = drive - strafe - turn;
            backLeftPower = drive - strafe + turn;
            backRightPower = drive + strafe - turn;


            if (Math.abs(frontLeftPower) > 1 || Math.abs(frontRightPower) > 1 || Math.abs(backLeftPower) > 1 || Math.abs(backRightPower) > 1){

                maxPower = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));

                //fix problem
                frontLeftPower /= maxPower;
                frontRightPower /= maxPower;
                backLeftPower /= maxPower;
                backRightPower /= maxPower;
            }

            robot.frontLeftMotor.setPower(frontLeftPower * maxMotorSpeed);
            robot.frontRightMotor.setPower(frontRightPower * maxMotorSpeed);
            robot.backRightMotor.setPower(backRightPower * maxMotorSpeed);
            robot.backLeftMotor.setPower(backLeftPower * maxMotorSpeed);




            //If slides are high enough, lets the lasy susan spin
            if (robot.slideMotor.getCurrentPosition() < (0.8*Constants.lowJunctionSlideTicks)){
                if (gamepad1.dpad_up){
                    robot.lazySusanSpinner.setTargetPosition(0);
                }
                else if(gamepad1.dpad_right){
                    robot.lazySusanSpinner.setTargetPosition(Constants.lasySusanRight);
                }
                else if (gamepad1.dpad_down){
                    robot.lazySusanSpinner.setTargetPosition(Constants.lasySusanBack);
                }
                else if (gamepad1.dpad_left){
                    robot.lazySusanSpinner.setTargetPosition((Constants.lasySusanLeft));
                }
            }


            //Lets it go to ground level if lazy susan is in the front/back
            if (((Math.abs(robot.lazySusanSpinner.getCurrentPosition()) < 5) ||
                    Math.abs((288 - robot.lazySusanSpinner.getCurrentPosition())) < 5) ||
                    (Math.abs(144 - robot.lazySusanSpinner.getCurrentPosition()) < 5)) {

                if (gamepad2.dpad_down) {
                    robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks);
                }

                if (gamepad2.y){
                    //4 Cone Stack
                    heightDifferential = Constants.coneBaseHeightTicks * 4;
                    robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + heightDifferential);
                }
                else if (gamepad2.x){
                    //3 Cone Stack
                    heightDifferential = Constants.coneBaseHeightTicks * 3;
                    robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + heightDifferential);
                }
                else if (gamepad2.b){
                    //2 Stack Cone
                    heightDifferential = Constants.coneBaseHeightTicks * 2;
                    robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + heightDifferential);
                }
                else if (gamepad2.a){
                    //1 Stack Cone
                    heightDifferential = Constants.coneBaseHeightTicks * 1;
                    robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + heightDifferential);
                }
            }

            //Lets it go up to a height above the side plates no matter what
            if (gamepad2.dpad_left) {
                robot.slideMotor.setTargetPosition(Constants.lowJunctionSlideTicks);
            } else if (gamepad2.dpad_right) {
                robot.slideMotor.setTargetPosition(Constants.middleJunctionSlideTicks);
            } else if (gamepad2.dpad_up) {
                robot.slideMotor.setTargetPosition(Constants.highJunctionSlideTicks);
            }



            if (gamepad2.dpad_down) {
                robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks);
            }
            else if (gamepad2.dpad_left) {
                robot.slideMotor.setTargetPosition(Constants.lowJunctionSlideTicks);
            }
            else if (gamepad2.dpad_right) {
                robot.slideMotor.setTargetPosition(Constants.middleJunctionSlideTicks);
            }
            else if(gamepad2.dpad_up){
                robot.slideMotor.setTargetPosition(Constants.highJunctionSlideTicks);
            }

//            if(gamepad2.left_bumper){
//                robot.clawServo.setPosition(Constants.clawServoOpenPosition);
//            }
//            else if(gamepad2.right_bumper) {
//                robot.clawServo.setPosition(Constants.clawServoClosedPosition);
//            }







            //TODO: Telemetry
            telemetry.addData("Front Left Motor Power: ", robot.frontLeftMotor.getPower());
            telemetry.addData("Front Right Motor Power: ", robot.frontRightMotor.getPower());
            telemetry.addData("Back Left Motor Power: ", robot.backLeftMotor.getPower());
            telemetry.addData("Back Right Motor Power: ", robot.backRightMotor.getPower());

            telemetry.addData("slideMotor Ticks ", robot.slideMotor.getCurrentPosition());

            telemetry.addData("Level: ", (heightDifferential-5)/Constants.coneBaseHeightTicks);

            telemetry.update();
        }
    }
}













