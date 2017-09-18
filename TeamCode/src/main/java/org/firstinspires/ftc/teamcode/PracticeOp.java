package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp: Teleop Tank", group="Pushbot")

public class PracticeOp extends OpMode {

  private DcMotor motorLeft;
  private DcMotor motorRight;

  private PracWheels wheels;

  public PracticeOp() {
    wheels = new PracWheels();
  }

  @Override
  public void init() {
    motorLeft = hardwareMap.dcMotor.get("MotorLeft");
    motorRight = hardwareMap.dcMotor.get("MotorRight");
    wheels.init(motorLeft, motorRight);
  }
    @Override
    public void start() {
    wheels.start();
  }











    public void loop() {
      // Gamepad sticks are inverted.
      wheels.move(-gamepad1.left_stick_y, -gamepad1.right_stick_y);


      telemetry.addData("Text", "*** Robot Data***");
    }
   @Override
      public void stop()  {
        wheels.stop();
      }
      }




