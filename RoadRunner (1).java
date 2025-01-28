// package org.firstinspires.ftc.teamcode.tuning;
// 
// import com.acmerobotics.roadrunner.Pose2d;
// import com.acmerobotics.roadrunner.Vector2d;
// import com.acmerobotics.roadrunner.ftc.Actions;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// 
// import org.firstinspires.ftc.teamcode.MecanumDrive;
// import org.firstinspires.ftc.teamcode.TankDrive;
// 
// public final class RoadRunner extends LinearOpMode {
//     @Override
//     public void runOpMode() {
//        
//             MecanumDrive drive = new MecanumDrive(hardwareMap);
// 
//           
//            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
//            .strafeRight(10)
//            .foward(5)
//            .build();
//            
//            waitForStart();
//            
//            if(isStopRequested()) return;
//            
//            drive.followTrajectory(myTrajectory);
//           
//         }
//     }
// 
// 
