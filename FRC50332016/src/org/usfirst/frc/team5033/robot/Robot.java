package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Components c = new Components(() -> isEnabled() && isAutonomous());

	Controls control = new Controls();

	RelayButtonUpdator shooterBallPusher;
	MotorControllerButtonUpdator liftObsticals;

	public void robotInit() {
	}

	public void autonomousInit() {
		c.resetAll();
		c.setSensors();

		try {
			c.auto.run(c.auto.routines, c);
		} catch (AutoEndException se) {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		c.resetAll();
		shooterBallPusher = new RelayButtonUpdator(c.shooterBallPusherMotor, control.xboxYButton(),
				control.xboxAButton());
		liftObsticals = new MotorControllerButtonUpdator(c.obsticalLiftingMotor, control.xboxXButton(),
				control.xboxBButton());
	}

	public void teleopPeriodic() {
		try {

			if (!control.joystickTrigger()) {
				c.tankDrive.arcadeDrive(-control.joystickY(), -control.joystickX() * Defines.ROBOT_TURNING_ADJUSTMENT);
			} else {
				c.tankDrive.arcadeDrive(control.joystickY(), -control.joystickX() * Defines.ROBOT_TURNING_ADJUSTMENT);
			}

			if (control.xboxLeftYAxis() < Defines.MINIMUM_AXIS_VALUE) {
				c.leftShooterMotor.set(Defines.LEFT_SHOOT_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_SHOOT_SPEED);
			} else if (control.xboxLeftYAxis() > Defines.MAXIMUM_AXIS_VALUE) {
				c.leftShooterMotor.set(Defines.LEFT_IMPELL_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_IMPELL_SPEED);
			} else {
				c.leftShooterMotor.set(Defines.SHOOTER_OFF);
				c.rightShooterMotor.set(Defines.SHOOTER_OFF);
			}

			c.shooterAngleMotor.set(control.xboxRightYAxis() / 3);
			shooterBallPusher.update();
			liftObsticals.update();

			VisionData vd = new VisionData(c);

			if (vd.isUsable() && control.xboxLeftTrigger() > Defines.MAXIMUM_AXIS_VALUE) {
				TeleopAutoAim autoAim = new TeleopAutoAim(c);

				autoAim.azimuthAiming();

				autoAim.indicators();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}