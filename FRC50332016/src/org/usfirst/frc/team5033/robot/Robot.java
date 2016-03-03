package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Curtis Johnston
 * @version 1
 * 
 * @description Team 5033's 2016 FIRST STRONGHOLD Code.
 * 
 */

public class Robot extends IterativeRobot {
	Components c = new Components(() -> isEnabled() && isAutonomous());

	Auto auto = new Auto();
	Defines.AutonomousRoutines routines;

	public void robotInit() {
		c.table = NetworkTable.getTable("SmartDashboard");

		auto.initializeSmartDashBoard(c);
	}

	public void autonomousInit() {
		final double distancePerPulse = Math.PI * Defines.WHEEL_DIAMETER / Defines.PULSE_PER_REVOLUTION
				/ Defines.ENCODER_GEAR_RATIO / Defines.GEAR_RATIO * Defines.FUDGE_FACTOR;
		c.rightDriveEncoder.setDistancePerPulse(distancePerPulse);
		c.leftDriveEncoder.setDistancePerPulse(distancePerPulse);
		c.gyro.reset();
		c.rightDriveEncoder.reset();
		c.leftDriveEncoder.reset();
		c.time.start();
		routines = (Defines.AutonomousRoutines) c.chooser.getSelected();
		auto.run(routines, c);
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		c.tankDrive = new RobotDrive(c.leftDrive, c.rightDrive);
	}

	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) {
			double shooterAxis = c.xbox.getRawAxis(Defines.XBOX_LEFT_Y_AXIS);
			double shooterAngleAxis = c.xbox.getRawAxis(Defines.XBOX_RIGHT_Y_AXIS);
			c.tankDrive.arcadeDrive(-c.joystick.getY(), -c.joystick.getX());

			if (shooterAxis < Defines.MINIMUM_Y_AXIS) {
				c.leftShooterMotor.set(Defines.LEFT_SHOOT_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_SHOOT_SPEED);
			} else if (shooterAxis > Defines.MAXIMUM_Y_AXIS) {
				c.leftShooterMotor.set(Defines.LEFT_IMPELL_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_IMPELL_SPEED);
			} else {
				c.leftShooterMotor.set(Defines.SHOOTER_OFF);
				c.rightShooterMotor.set(Defines.SHOOTER_OFF);
			}
			if (shooterAngleAxis < Defines.MINIMUM_Y_AXIS) {
				c.shooterAngleMotor.set(Relay.Value.kForward);
			} else if (shooterAngleAxis > Defines.MAXIMUM_Y_AXIS) {
				c.shooterAngleMotor.set(Relay.Value.kReverse);
			} else {
				c.shooterAngleMotor.set(Relay.Value.kOff);
			}
		}
	}
}