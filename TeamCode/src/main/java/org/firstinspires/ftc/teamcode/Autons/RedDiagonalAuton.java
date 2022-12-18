package org.firstinspires.ftc.teamcode.Autons;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RRdrive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.customUtil.ColorDetector;
import org.firstinspires.ftc.teamcode.customUtil.Constants;
import org.firstinspires.ftc.teamcode.customUtil.RobotHardwareMap;


@Autonomous(name="Red Diagonal Auton", group="Auton")
public class RedDiagonalAuton extends LinearOpMode {

    RobotHardwareMap robot = new RobotHardwareMap();

    @Override
    public void runOpMode() {
        robot.initialize(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        ColorDetector colorDetector = new ColorDetector(robot.colorSensor);
        String color = "red";


        //Make Trajectories
        Trajectory toBeacon = drive.trajectoryBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(16.5, 3.75, Math.toRadians(28)))
                .build();

        Trajectory scorePreloadedCone = drive.trajectoryBuilder(toBeacon.end())
                .lineToLinearHeading(new Pose2d(65, 3, Math.toRadians(-90)))
                .build();

        Trajectory intakeCone1Step1 = drive.trajectoryBuilder(scorePreloadedCone.end())
                .lineToLinearHeading(new Pose2d(64.5, 9, Math.toRadians(-90)))
                .build();

        Trajectory intakeCone1Step2 = drive.trajectoryBuilder(intakeCone1Step1.end())
                .lineToLinearHeading(new Pose2d(47, 9, Math.toRadians(-90)))
                .build();

        Trajectory intakeCone1Step3 = drive.trajectoryBuilder(intakeCone1Step2.end())
                .lineToLinearHeading(new Pose2d(47, 8.75, Math.toRadians(90)))
                .build();

        Trajectory intakeCone1Step4 = drive.trajectoryBuilder(intakeCone1Step3.end())
                .lineToLinearHeading(new Pose2d(47, 36, Math.toRadians(90)))
                .build();

        Trajectory scoreCone1Step1 = drive.trajectoryBuilder(intakeCone1Step4.end())
                .lineToLinearHeading(new Pose2d(47, 6.75, Math.toRadians(90)))
                .build();

        Trajectory scoreCone1Step2 = drive.trajectoryBuilder(scoreCone1Step1.end())
                .lineToLinearHeading(new Pose2d(47, 5, Math.toRadians(-35)))
                .build();

        Trajectory scoreCone1Step3 = drive.trajectoryBuilder(scoreCone1Step2.end())
                .lineToLinearHeading(new Pose2d(55.75, -2.75, Math.toRadians(-35)))
                .build();

        Trajectory intakeCone2Step1 = drive.trajectoryBuilder(scoreCone1Step3.end())
                .lineToLinearHeading(new Pose2d(48, 7.25, Math.toRadians(-35)))
                .build();

        Trajectory intakeCone2Step2 = drive.trajectoryBuilder(intakeCone2Step1.end())
                .lineToLinearHeading(new Pose2d(48, 6.75, Math.toRadians(90)))
                .build();

        Trajectory intakeCone2Step3 = drive.trajectoryBuilder(intakeCone2Step2.end())
                .lineToLinearHeading(new Pose2d(48, 36, Math.toRadians(90)))
                .build();

        Trajectory ScoreCone3Step1 = drive.trajectoryBuilder(intakeCone2Step3.end())
                .lineToLinearHeading(new Pose2d(48, -8, Math.toRadians(0)))
                .build();

        Trajectory ScoreCone3Step2 = drive.trajectoryBuilder(ScoreCone3Step1.end())
                .lineToLinearHeading(new Pose2d(58, -6, Math.toRadians(0)))
                .build();



        Trajectory parkInRedGreen1 = drive.trajectoryBuilder(ScoreCone3Step2.end())
                .lineToLinearHeading(new Pose2d(48, -7, Math.toRadians(0)))
                .build();

        Trajectory parkInRed = drive.trajectoryBuilder(parkInRedGreen1.end())
                .lineToLinearHeading(new Pose2d(48, 12, Math.toRadians(0)))
                .build();

        Trajectory parkInGreen = drive.trajectoryBuilder((parkInRedGreen1).end())
                .lineToLinearHeading(new Pose2d(48, -38, Math.toRadians(0)))
                .build();

        Trajectory parkInBlue1 = drive.trajectoryBuilder((intakeCone2Step3).end())
                .lineToLinearHeading(new Pose2d(48, 27.5, Math.toRadians(90)))
                .build();

        Trajectory parkInBlue2 = drive.trajectoryBuilder((parkInBlue1).end())
                .lineToLinearHeading(new Pose2d(48.1, 27.5, Math.toRadians(0)))
                .build();








        robot.clawServo.setPosition(Constants.clawServoClosedPosition);
        waitForStart();


        //PATH
        drive.followTrajectory(toBeacon);

        color = colorDetector.getColor();
        telemetry.addData("Color: ", color);
        telemetry.update();
        robot.slideMotor.setTargetPosition(Constants.highJunctionSlideTicks);
        sleep(200);


        drive.followTrajectory(scorePreloadedCone);
        sleep(300);
        robot.clawServo.setPosition(0);
        sleep(200);
        drive.followTrajectory(intakeCone1Step1);
        sleep(200);
        drive.followTrajectory(intakeCone1Step2);
        robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + (5 * Constants.coneBaseHeightTicks));
        sleep(200);
        drive.followTrajectory(intakeCone1Step3);
        drive.followTrajectory(intakeCone1Step4);
        robot.clawServo.setPosition(Constants.clawServoClosedPosition);
        sleep(300);
        robot.slideMotor.setTargetPosition(Constants.highJunctionSlideTicks);
        sleep(200);
        drive.followTrajectory(scoreCone1Step1);
        drive.followTrajectory(scoreCone1Step2);
        drive.followTrajectory(scoreCone1Step3);
        robot.clawServo.setPosition(0);
        sleep(200);
        drive.followTrajectory(intakeCone2Step1);
        robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + (4 * Constants.coneBaseHeightTicks));
        drive.followTrajectory(intakeCone2Step2);
        drive.followTrajectory(intakeCone2Step3);
        robot.clawServo.setPosition(Constants.clawServoClosedPosition);
        sleep(300);
        robot.slideMotor.setTargetPosition(Constants.highJunctionSlideTicks);
        sleep(200);

        if(color.equals("green")){
            drive.followTrajectory(ScoreCone3Step1);
            drive.followTrajectory(ScoreCone3Step2);
            robot.clawServo.setPosition(Constants.clawServoOpenPosition);
            drive.followTrajectory(parkInRedGreen1);
            drive.followTrajectory(parkInGreen);
            robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks);
            sleep(1000);
        }
        else if (color.equals("red")){
            drive.followTrajectory(ScoreCone3Step1);
            drive.followTrajectory(ScoreCone3Step2);
            robot.clawServo.setPosition(Constants.clawServoOpenPosition);
            sleep(250);
            drive.followTrajectory(parkInRedGreen1);
            drive.followTrajectory(parkInRed);
            robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks);
            sleep(1000);
        }
        else{
            robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks);
            robot.slideMotor.setTargetPosition(Constants.slideGroundLevelTicks + (4 * Constants.coneBaseHeightTicks));
        }
        //BLUE DOES NOTHING










        if (opModeIsActive()){

        }

    }
}