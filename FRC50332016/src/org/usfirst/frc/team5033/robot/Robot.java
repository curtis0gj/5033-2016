package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Components c = new Components(() -> isEnabled() && isAutonomous());

	Controls control = new Controls();

	RelayUpdater shooterBallPusher;
	MotorControllerUpdater liftObsticals;
	MotorControllerUpdater shootBalls;

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
		shooterBallPusher = new RelayUpdater(c.shooterBallPusherMotor, control.xboxYButton(), control.xboxAButton());
		liftObsticals = new MotorControllerUpdater(c.obsticalLiftingMotor, null, null, control.xboxXButton(),
				control.xboxBButton(), null);
		shootBalls = new MotorControllerUpdater(null, c.leftShooterMotor, c.rightShooterMotor, null, null,
				control.xboxLeftYAxis());
	}

	public void teleopPeriodic() {
		try {

			if (!control.joystickTrigger()) {
				c.tankDrive.arcadeDrive(-control.joystickY(), -control.joystickX() * Defines.ROBOT_TURNING_ADJUSTMENT);
			} else {
				c.tankDrive.arcadeDrive(control.joystickY(), -control.joystickX() * Defines.ROBOT_TURNING_ADJUSTMENT);
			}

			c.shooterAngleMotor.set(control.xboxRightYAxis() / 3);

			shooterBallPusher.update();
			liftObsticals.update();
			shootBalls.update2();

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