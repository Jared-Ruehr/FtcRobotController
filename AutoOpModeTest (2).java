// /* Copyright (c) 2017 FIRST. All rights reserved.
//  *
//  * Redistribution and use in source and binary forms, with or without modification,
//  * are permitted (subject to the limitations in the disclaimer below) provided that
//  * the following conditions are met:
//  *
//  * Redistributions of source code must retain the above copyright notice, this list
//  * of conditions and the following disclaimer.
//  *
//  * Redistributions in binary form must reproduce the above copyright notice, this
//  * list of conditions and the following disclaimer in the documentation and/or
//  * other materials provided with the distribution.
//  *
//  * Neither the name of FIRST nor the names of its contributors may be used to endorse or
//  * promote products derived from this software without specific prior written permission.
//  *
//  * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
//  * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
//  * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
//  * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
//  * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
//  * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
//  * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
//  * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
//  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//  */
// 
// package org.firstinspires.ftc.teamcode;
// 
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.eventloop.opmode.Disabled;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.util.ElapsedTime;
// 
// /*
//  * This OpMode illustrates the concept of driving a path based on encoder counts.
//  * The code is structured as a LinearOpMode
//  *
//  * The code REQUIRES that you DO have encoders on the wheels,
//  *   otherwise you would use: RobotAutoDriveByTime;
//  *
//  *  This code ALSO requires that the drive Motors have been configured such that a positive
//  *  power command moves them forward, and causes the encoders to count UP.
//  *
//  *   The desired path in this example is:
//  *   - Drive forward for 48 inches
//  *   - Spin right for 12 Inches
//  *   - Drive Backward for 24 inches
//  *   - Stop and close the claw.
//  *
//  *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
//  *  that performs the actual movement.
//  *  This method assumes that each movement is relative to the last stopping place.
//  *  There are other ways to perform encoder based moves, but this method is probably the simplest.
//  *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
//  *
//  * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
//  * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
//  */
// 
// @Autonomous(name="Robot: Auto Drive By Encoder", group="Robot")
// 
// public class AutoOpModeTest extends LinearOpMode {
// 
//     /* Declare OpMode members. */
//     {
//     //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
//     //  applied to the drive motors to correct the error.
//     //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
//     //final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
//     //final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
//     //final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
// 
//     //final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
//     //final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value (adjust for your robot)
//     //final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)
// }
//     private DcMotor         leftFront   = null;
//     private DcMotor         rightFront  = null;
//     private DcMotor         leftBack    = null;
//     private DcMotor         rightBack   = null;
//     private DcMotor         gripLift    = null;
//     private Servo           clawServo   = null; 
//     private DcMotor         hangingMotor = null;
//     private ElapsedTime     runtime = new ElapsedTime();
// 
//     // Calculate the COUNTS_PER_INCH for your specific drive train.
//     // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
//     // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
//     // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
//     // This is gearing DOWN for less speed and more torque.
//     // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
//     static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;    // eg: TETRIX Motor Encoder
//     static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
//     static final double     WHEEL_DIAMETER_INCHES   = 3.75 ;     // For figuring circumference
//     static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
//                                                       (WHEEL_DIAMETER_INCHES * 3.1415);
//     static final double     DRIVE_SPEED             = 0.6;
//     static final double     TURN_SPEED              = 0.4;
// 
//     @Override
//     public void runOpMode() {
// 
//         // Initialize the drive system variables.
//         leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
//         rightFront = hardwareMap.get(DcMotor.class, "rightFront");
//         leftBack = hardwareMap.get(DcMotor.class, "leftBack");
//         rightBack = hardwareMap.get(DcMotor.class,"rightBack");
//         gripLift = hardwareMap.get(DcMotor.class,"griplift")
//         clawServo = hardwareMap.get(Servo.class,"clawServo")
//         hangingMotor = hardwareMap.get(DcMotor.class,"hangingMotor")
//
//         // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
//         // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
//         // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
//         leftFront.setDirection(DcMotor.Direction.FORWARD);
//         rightFront.setDirection(DcMotor.Direction.REVERSE);
//         leftBack.setDirection(DcMotor.Direction.REVERSE);
//         rightBack.setDirection(DcMotor.Direction.FORWARD);
//         hangingMotor.setDirection(DcMotor.Direction.REVERSE);
//         gripLift.setDirection(DcMotor.Direction.FORWARD);
// 
//         leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         
//         leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//         rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
// 
//         // Send telemetry message to indicate successful Encoder reset
//         telemetry.addData("Starting at",  "%7d :%7d",
//                           leftFront.getCurrentPosition(),
//                           rightFront.getCurrentPosition(),
//                           leftBack.getCurrentPosition(),
//                           rightBack.getCurrentPosition());
//         telemetry.update();
// 
//         // Wait for the game to start (driver presses START)
//         waitForStart();
// 
//         // Step through each leg of the path,
//         // Note: Reverse movement is obtained by setting a negative distance (not speed)
//         encoderDrive(DRIVE_SPEED, 31,  31,0, 5.0);
//         encoderDrive(TURN_SPEED,  12, -12, 0,4.0);
//         encoderDrive(DRIVE_SPEED, -24, -24, 0,4.0);
//         
//         //move linear slide rails up to the tops bars hieght 
//         encoderDrive(DRIVE_SPEED, 0, 0,36,4.0);
//
//         //move forwards until robot hits the submersable
//           // S1: Forward 33 Inches with 5 Sec timeout
//         
//      //move slide rail downward to hang specimin
//      encoderDrive(DRIVE_SPEED, 0, 0,-36,4.0);
//      //release the grip of the claw
//      encoderDrive(DRIVE_SPEED, 0, 0, 0, ,4.0);
//      //move robot backwards until it hits the wall
//      //encoderDrive(DRIVE_SPEED,  -43,  -13, 5.0);  // S1: backwards 33 Inches with 5 Sec timeout
//      //move linear slide rail backwards
//      
//      //turn towards parking zone
//      //encoderDrive(DRIVE_SPEED,  23,  -33, 1.0); // S1: Right side: 33inches Left side:
//      //drive forwards towards parking zone
//      //encoderDrive(DRIVE_SPEED,  53,  33, 5.0); // S1: Forward 33 Inches with 5 Sec timeout
//      
//         
//         
//        // telemetry.addData("Path", "error");
//         telemetry.addData("Path", "Complete");
//         telemetry.update();
//         sleep(1000);  // pause to display final telemetry message.
//         
//         
//         
//     }
// 
//     /*
//      *  Method to perform a relative move, based on encoder counts.
//      *  Encoders are not reset as the move is based on the current position.
//      *  Move will stop if any of three conditions occur:
//      *  1) Move gets to the desired position
//      *  2) Move runs out of time
//      *  3) Driver stops the OpMode running.
//      */
//     public void encoderDrive(double speed,
//                              double leftInches, double rightInches,
//                              double timeoutS) {
//         int newLeftTarget;
//         int newRightTarget;
// 
//         // Ensure that the OpMode is still active
//         if (opModeIsActive()) {
// 
//             // Determine new target position, and pass to motor controller
//             newLeftTarget = leftFront.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
//             newLeftTarget = leftBack.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
//             newRightTarget = rightFront.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
//             newRightTarget = rightBack.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
// 
//             leftFront.setTargetPosition(newLeftTarget);
//             rightFront.setTargetPosition(newRightTarget);
//             leftBack.setTargetPosition(newLeftTarget);
//             rightBack.setTargetPosition(newRightTarget);
// 
//             // Turn On RUN_TO_POSITION
//             leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//             rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//             leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//             rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
// 
//             // reset the timeout time and start motion.
//             runtime.reset();
//             leftFront.setPower(Math.abs(speed));
//             rightFront.setPower(Math.abs(speed));
//             leftBack.setPower(Math.abs(speed));
//             rightBack.setPower(Math.abs(speed));
// 
//             // keep looping while we are still active, and there is time left, and both motors are running.
//             // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
//             // its target position, the motion will stop.  This is "safer" in the event that the robot will
//             // always end the motion as soon as possible.
//             // However, if you require that BOTH motors have finished their moves before the robot continues
//             // onto the next step, use (isBusy() || isBusy()) in the loop test.
//             while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (leftFront.isBusy() && rightFront.isBusy()) &&
//                    (leftBack.isBusy() && rightBack.isBusy())) {
// 
//                    
//         
//             
//                   
// 
//                 // Display it for the driver.
//                 telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
//                 telemetry.addData("Currently at",  " at %7d :%7d",
//                 leftFront.getCurrentPosition(),rightFront.getCurrentPosition(),leftBack.getCurrentPosition(), rightBack.getCurrentPosition());
//                 telemetry.update();
//                    }
// 
//             // Stop all motion;
//             leftFront.setPower(0);
//             rightFront.setPower(0);
//             leftBack.setPower(0);
//             rightBack.setPower(0);
// 
//             // Turn off RUN_TO_POSITION
//             leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//             rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//             leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//             rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
// 
//             sleep(250);   // optional pause after each move.
//             
//         
//     }
// 
//        }
//    }
//     
//     
// //}
