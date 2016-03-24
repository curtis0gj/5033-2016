package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
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
	Defines.AutonomousRoutines routines;

	public void robotInit() {

	}

	public void autonomousInit() {
		final double distancePerPulse = Math.PI * Defines.WHEEL_DIAMETER / Defines.PULSE_PER_REVOLUTION
				/ Defines.ENCODER_GEAR_RATIO / Defines.GEAR_RATIO * Defines.FUDGE_FACTOR;
		c.rightDriveEncoder.setDistancePerPulse(distancePerPulse);
		c.gyro.reset();
		c.rightDriveEncoder.reset();
		c.shooterAngleEncoder.reset();
		c.time.start();
		routines = (Defines.AutonomousRoutines) c.chooser.getSelected();

		try {
			c.auto.run(routines, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autonomousPeriodic() {

	}

	public void teleopInit() {
		c.tankDrive = new RobotDrive(c.leftDrive, c.rightDrive);
		c.shooterAngleEncoder.reset();
	}

	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) {
			try {
				double shooterAxis = c.xbox.getRawAxis(Defines.XBOX_LEFT_Y_AXIS);
				double shooterAngleAxis = c.xbox.getRawAxis(Defines.XBOX_RIGHT_Y_AXIS);
				double obsticalManipulatorAxis = c.xbox.getRawAxis(Defines.XBOX_D_PAD);
				double shooterAngle = c.shooterAngleEncoder.get();
				boolean pushBallOutButton = c.xbox.getRawButton(Defines.XBOX_Y_BUTTON);
				boolean reverseBallPusherMotorButton = c.xbox.getRawButton(Defines.XBOX_A_BUTTON);
				boolean invertDriveDirectionButton = c.joystick.getRawButton(Defines.JOYSTICK_TRIGGER);
				boolean disableShooterAngling = c.xbox.getRawButton(Defines.XBOX_BACK_BUTTON);
				boolean shooterUp = false;
				boolean shooterDown = false;

				SmartDashboard.putNumber("testing shooter angle", shooterAngle);
				SmartDashboard.putNumber("testing robot angle", c.gyro.getAngle());

				if (!invertDriveDirectionButton) {
					c.tankDrive.arcadeDrive(-c.joystick.getY(), -c.joystick.getX());
				} else if (invertDriveDirectionButton) {
					c.tankDrive.arcadeDrive(c.joystick.getY(), -c.joystick.getX());
				}

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

				// Shooter angle from centerline 54 - 55 degrees.
				// Shooter angle 48 at 14ft and was in.
				if (shooterAngleAxis < Defines.MINIMUM_Y_AXIS) {
					shooterUp = true;
				} else if (shooterAngleAxis > Defines.MAXIMUM_Y_AXIS) {
					shooterDown = true;
				} else {

				}

				if (disableShooterAngling) {
					shooterUp = false;
					shooterDown = false;
				}

				if (shooterUp) {
					if (shooterAngle < Defines.UP_SHOOTER_ANGLE_INTERVAL) {
						c.shooterAngleMotor.set(Relay.Value.kReverse);
					} else {
						c.shooterAngleMotor.set(Relay.Value.kOff);
						c.shooterAngleEncoder.reset();
						shooterUp = false;
					}
				}

				if (shooterDown) {
					if (shooterAngle > Defines.DOWN_SHOOTER_ANGLE_INTERVAL) {
						c.shooterAngleMotor.set(Relay.Value.kForward);
					} else {
						c.shooterAngleMotor.set(Relay.Value.kOff);
						c.shooterAngleEncoder.reset();
						shooterDown = false;
					}
				}

				if (pushBallOutButton) {
					c.shooterBallPusherMotor.set(Relay.Value.kForward);
				} else if (reverseBallPusherMotorButton) {
					c.shooterBallPusherMotor.set(Relay.Value.kReverse);
				} else {
					c.shooterBallPusherMotor.set(Relay.Value.kOff);
				}

				if (obsticalManipulatorAxis == 0) {
					// Up.
				} else if (obsticalManipulatorAxis == 180) {
					// Down.
				} else {
					// Off.
				}

				VisionData vd = new VisionData(c);

				if (vd.isUsable()) {
					if (Math.abs(Defines.SHOOTER_RANGE - vd.visionDistance) <= Defines.SHOOTER_TOLERANCE) {
						SmartDashboard.putBoolean("SHOOTER DISTANCE CHECK", true);
					} else {
						SmartDashboard.putBoolean("SHOOTER DISTANCE CHECK", false);
					}
					if (vd.azimuth >= Defines.MAX_AZIMUTH || vd.azimuth <= Defines.MIN_AZIMUTH) {
						SmartDashboard.putBoolean("SHOOTER AZIMUTH CHECK", true);
					} else {
						SmartDashboard.putBoolean("SHOOTER AZIMUTH CHECK", false);
					}
				} else {
					SmartDashboard.putBoolean("SHOOTER DISTANCE CHECK", false);
					SmartDashboard.putBoolean("SHOOTER AZIMUTH CHECK", false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}