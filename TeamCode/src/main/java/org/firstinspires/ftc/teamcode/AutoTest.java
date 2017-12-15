package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "AutoTest", group = "Pushbot")

public class AutoTest extends OpMode {

    private DcMotor leftSide;
    private DcMotor rightSide;
    private DcMotor elevatorMotor;
    private Servo servoLeft;
    private Servo servoRight;

    private double Speed = -0.4;  //Speed of motor

    private ElapsedTime timer = new ElapsedTime();
    private boolean moveEle = true;

    private void setLeftPower(double pwr) {
        double leftPower = Range.clip(pwr, -1, 1);
        leftSide.setPower(leftPower);
    }

    private void setRightPower(double pwr) {
        double rightPower = Range.clip(pwr, -1, 1);
        rightSide.setPower(rightPower);
    }

    private void setWheels(double l, double r) {
        setLeftPower(l);
        setRightPower(r);
    }

    private void move() {
        setWheels(Speed, Speed);
    }

    private void turn() {
        setWheels(Speed, -Speed);
    }

    private void moveForward() {
        setWheels(-Speed, -Speed);
    }

    private void setWheelMode(DcMotor.RunMode mode) {
        leftSide.setMode(mode);
        rightSide.setMode(mode);
    }

    @Override
    public void stop() {
        setWheels(0, 0);
        elevatorMotor.setPower(0);
        servoLeft.setPosition(1);
        servoRight.setPosition(1);
    }

    @Override
    public void start() {
        setWheels(0.0, 0.0);
        timer.reset();
        telemetry.addData("Start", "");
    }

    @Override
    public void loop() {

        telemetry.addData("Time", timer.time());

        if (moveEle) {
            moveEle = false;
            servoLeft.setPosition(0.5);
            servoRight.setPosition(0.5);
            elevatorMotor.setPower(1);
        } else {
            double grabTime = 0.5;
            while (timer.time() <= grabTime) {
                servoLeft.setPosition(0.5);
                servoRight.setPosition(0.5);
                elevatorMotor.setPower(1);
            }
            double straightDur = 2;
            while (timer.time() <= straightDur && timer.time() > grabTime) {
                elevatorMotor.setPower(0);
                move();
            }
            double turnDur = 2.5;
            while (timer.time() > straightDur && timer.time() <= turnDur) {
                turn();
            }
            double pushDur = 3.5;
            while (timer.time() > turnDur && timer.time() <= pushDur) {
                moveForward();
            }
            double backupTime = 3.7;
            if (timer.time() > pushDur && timer.time() <= backupTime) {
                setWheels(0, 0);
                elevatorMotor.setPower(-1);
                servoLeft.setPosition(1);
                servoRight.setPosition(1);
            } else {
                elevatorMotor.setPower(0);
            }
            double endTime = 3.4;
            if (timer.time() > backupTime && timer.time() <= endTime) {
                setWheels(-1, -1);
            } else {
                stop();
            }
        }
    }

    @Override
    public void init() {
        leftSide = hardwareMap.dcMotor.get("MotorLeft");
        rightSide = hardwareMap.dcMotor.get("MotorRight");
        elevatorMotor = hardwareMap.dcMotor.get("ElevatorMotor");
        servoLeft = hardwareMap.servo.get("ServoLeft");
        servoRight = hardwareMap.servo.get("ServoRight");

        leftSide.setDirection(DcMotor.Direction.REVERSE);
        rightSide.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
        servoLeft.setDirection(Servo.Direction.FORWARD);
        servoRight.setDirection(Servo.Direction.REVERSE);

        setWheelMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Init", "");
    }
}
