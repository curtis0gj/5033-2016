package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;

public class Robot extends IterativeRobot {
	Components c = new Components(() -> isEnabled() && isAutonomous());
	Defines.AutonomousRoutines routines;

	public void robotInit() {
		c.tankDrive.setSafetyEnabled(false);
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

			if (!c.control.joystickTrigger()) {
				c.tankDrive.arcadeDrive(-c.control.joystickY(), -c.control.joystickX() * 0.8);
			} else {
				c.tankDrive.arcadeDrive(c.control.joystickY(), -c.control.joystickX() * 0.8);
			}

			if (c.control.xboxLeftYAxis() < Defines.MINIMUM_AXIS_VALUE) {
				c.leftShooterMotor.set(Defines.LEFT_SHOOT_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_SHOOT_SPEED);
			} else if (c.control.xboxLeftYAxis() > Defines.MAXIMUM_AXIS_VALUE) {
				c.leftShooterMotor.set(Defines.LEFT_IMPELL_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_IMPELL_SPEED);
			} else {
				c.leftShooterMotor.set(Defines.SHOOTER_OFF);
				c.rightShooterMotor.set(Defines.SHOOTER_OFF);
			}

			c.shooterAngleMotor.set(c.control.xboxRightYAxis() / 3);

			if (c.control.xboxYButton()) {
				c.shooterBallPusherMotor.set(Relay.Value.kForward);
			} else if (c.control.xboxAButton()) {
				c.shooterBallPusherMotor.set(Relay.Value.kReverse);
			} else {
				c.shooterBallPusherMotor.set(Relay.Value.kOff);
			}

			if (c.control.xboxXButton()) {
				c.obsticalLiftingMotor.set(Defines.LIFT_OBSTICAL_SPEED);
			} else if (c.control.xboxBButton()) {
				c.obsticalLiftingMotor.set(Defines.LOWER_OBSTICAL_SPEED);
			} else {
				c.obsticalLiftingMotor.set(Defines.OBSTICAL_OFF);
			}

			VisionData vd = new VisionData(c);

			if (vd.isUsable() && c.control.xboxLeftTrigger() > Defines.MAXIMUM_AXIS_VALUE) {
				TeleopAutoAim autoAim = new TeleopAutoAim(c);

				autoAim.azimuthAiming();

				autoAim.indicators();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}