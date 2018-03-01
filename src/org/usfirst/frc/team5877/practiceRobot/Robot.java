/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5877.practiceRobot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	private Spark arm;
	private SpeedControllerGroup left, right;
	private DifferentialDrive m_myRobot;
	private XboxController playerOne, playerTwo;
	private double output = 0;
	private Solenoid clawOpen, clawClose, liftUp, liftDown;
	private double fSpeed = 0, tSpeed = 0, deadZone = 0.05;

	@Override
	public void robotInit() {
		playerOne = new XboxController(0);
		playerTwo = new XboxController(1);
		arm = new Spark(4);
		left = new SpeedControllerGroup(new Victor(0), new Victor(2));
		right = new SpeedControllerGroup(new Victor(1), new Victor(3));
		m_myRobot = new DifferentialDrive(left, right);
		
		clawOpen = new Solenoid(6, 0);
		clawClose = new Solenoid(6, 1);
		liftUp = new Solenoid(6, 2);
		liftDown = new Solenoid(6, 3);
		
		clawOpen.set(true);
		clawClose.set(false);
	}

	@Override
	public void teleopPeriodic() {
		//left.get()
//		fSpeed += ((-playerOne.getY(Hand.kLeft) - left.get())/8);
//		tSpeed += (playerOne.getX(Hand.kRight) - left.get())/8;
		System.out.println(playerOne.getY(Hand.kLeft));
		if(-playerOne.getY(Hand.kLeft) >= deadZone || -playerOne.getY(Hand.kLeft) <= -deadZone || playerOne.getX(Hand.kRight) >= deadZone || playerOne.getX(Hand.kRight) <= -deadZone)
			m_myRobot.arcadeDrive(-playerOne.getY(Hand.kLeft), playerOne.getX(Hand.kRight));
		
		if(playerTwo.getBButton()) {
			output += (((playerTwo.getY(Hand.kLeft)*.75) - arm.get()) / 8);
			arm.set(output);
		} else {
			output += (((playerTwo.getY(Hand.kLeft)*.35) - arm.get()) / 8);
			arm.set(output);
		}
		
		if(playerTwo.getAButton() && false) {
			liftUp.set(true);
			liftDown.set(false);
		} else if(playerTwo.getYButton() && false){
			liftUp.set(false);
			liftUp.set(true);
		}
		
		if(playerTwo.getBumperReleased(Hand.kRight)) {
			clawOpen.set(true);
			clawClose.set(false);
		} else if(playerTwo.getBumperReleased(Hand.kLeft)){
			clawOpen.set(false);
			clawClose.set(true);
		}
	}

	@Override
	public void autonomousInit() {
		clawOpen = new Solenoid(6, 0);
		clawClose = new Solenoid(6, 1);
		
		clawOpen.set(true);
		clawClose.set(false);
	}
	
	@Override
	public void autonomousPeriodic() { 
		clawClose.set(true);
		clawOpen.set(false);
	}
}