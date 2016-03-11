package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Curtis Johnston
 * @version 1
 * 
 * @description Team 5033's Armatec Beavertronics code for the 2016 game
 *              STRONGHOLD.
 * 
 */

public class Robot extends IterativeRobot {
	Components c = new Components(() -> isEnabled() && isAutonomous());

	// VisionData vd = new VisionData();
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
		c.gyro.reset();
		c.rightDriveEncoder.reset();
		c.time.start();
		routines = (Defines.AutonomousRoutines) c.chooser.getSelected();

		try {
			auto.run(routines, c);
		} catch (Exception e) {
		}
	}

	public void autonomousPeriodic() {

	}

	public void teleopInit() {
		c.tankDrive = new RobotDrive(c.leftDrive, c.rightDrive);
	}

	public void teleopPeriodic() {
		try {
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
				/*if (vd.visionDistance == Defines.SHOOTER_RANGE) {
					SmartDashboard.putBoolean("SHOOTER DISTANCE CHECK", true);
				} else {
					SmartDashboard.putBoolean("SHOOTER DISTANCE CHECK", false);
				}
				if (vd.azimuth >= Defines.MAX_AZIMUTH || vd.azimuth <= Defines.MIN_AZIMUTH) {
					SmartDashboard.putBoolean("SHOOTER AZIMUTH CHECK", true);
				} else {
					SmartDashboard.putBoolean("SHOOTER AZIMUTH CHECK", false);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}