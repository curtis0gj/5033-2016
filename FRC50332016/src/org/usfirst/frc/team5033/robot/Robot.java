package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		c.time.start();

		c.resetAll();

		routines = (Defines.AutonomousRoutines) c.chooser.getSelected();

		try {
			c.auto.run(routines, c);
		} catch (AutoEndException se) {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autonomousPeriodic() {

	}

	public void teleopInit() {
		c.resetAll();
	}

	public void teleopPeriodic() {
		try {
			double shooterAxis = c.xbox.getRawAxis(Defines.XBOX_LEFT_Y_AXIS);
			double shooterAngleAxis = c.xbox.getRawAxis(Defines.XBOX_RIGHT_Y_AXIS);
			boolean liftManipulatorButton = c.xbox.getRawButton(Defines.XBOX_X_BUTTON);
			boolean lowerManipulatorButton = c.xbox.getRawButton(Defines.XBOX_B_BUTTON);
			boolean pushBallOutButton = c.xbox.getRawButton(Defines.XBOX_Y_BUTTON);
			boolean reverseBallPusherMotorButton = c.xbox.getRawButton(Defines.XBOX_A_BUTTON);
			boolean invertDriveDirectionButton = c.joystick.getRawButton(Defines.JOYSTICK_TRIGGER);

			if (!invertDriveDirectionButton) {
				c.tankDrive.arcadeDrive(-c.joystick.getY(), -c.joystick.getX());
			} else {
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

			c.shooterAngleMotor.set(shooterAngleAxis / 3);

			if (pushBallOutButton) {
				c.shooterBallPusherMotor.set(Relay.Value.kForward);
			} else if (reverseBallPusherMotorButton) {
				c.shooterBallPusherMotor.set(Relay.Value.kReverse);
			} else {
				c.shooterBallPusherMotor.set(Relay.Value.kOff);
			}

			if (liftManipulatorButton) {
				c.obsticalLiftingMotor.set(Defines.LIFT_OBSTICAL_SPEED);
			} else if (lowerManipulatorButton) {
				c.obsticalLiftingMotor.set(Defines.LOWER_OBSTICAL_SPEED);
			} else {
				c.obsticalLiftingMotor.set(Defines.OBSTICAL_OFF);
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