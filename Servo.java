package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class Servo extends LinearOpMode {
    Servo servo;
    
    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, "Servo");
        waitForStart();
        boolean clawActive = false;
        double position = 1;
        
        while(opModeIsActive()){
            if(gamepad1.dpad_down && clawActive == false) {
                position -= 0.01;
                servo.setPosition(position);
                clawActive = true;
            } else if (!gamepad1.dpad_down) {
                clawActive = false;
            }
            if(gamepad1.dpad_up){
                servo.setPosition(1);
                
            }
            telemetry.addData("Claw Position", position);
            telemetry.update();
        }
                
    }
    
    
    // todo: write your code here
}
