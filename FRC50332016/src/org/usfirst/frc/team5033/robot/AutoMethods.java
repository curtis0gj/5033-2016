package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.Relay;

public abstract class AutoMethods {
	Components c;
	VisionData vd;

	public AutoMethods(Components c) {
		this.c = c;
		vd = new VisionData(c);
	}

	/*
	 * public double clamp(double num, double low, double high) { return
	 * Math.max(low, Math.min(num, high)); }
	 */

	/*
	 * public double calcSpeedForGyroscopeTurningLeftDrive(double currentAngle)
	 * { return clamp(Math.tan(Math.toRadians(currentAngle)) / 0.7, -0.35,
	 * 0.35); }
	 * 
	 * public double calcSpeedForGyroscopeTurningRightDrive(double currentAngle)
	 * { return calcSpeedForGyroscopeTurningLeftDrive(-currentAngle); }
	 */

	public void gyroTurning(double desiredAngle) throws AutoEndException {
		// - 90 for a desired angle is left and + 90 is right.
		double startingAngle = c.gyro.getAngle();

		while (c.isAuto()) {
			double currentAngle = c.gyro.getAngle();
			double delta = (desiredAngle - (currentAngle - startingAngle));

			if (Math.abs(delta) < 4) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				break;
			} else {
				c.leftDrive.set(CalcSpeed.calcSpeedForGyroscopeTurning(delta, CalcSpeed.LEFT));
				c.rightDrive.set(CalcSpeed.calcSpeedForGyroscopeTurning(delta, CalcSpeed.RIGHT));
				// c.leftDrive.set(calcSpeedForGyroscopeTurningLeftDrive(delta));
				// c.rightDrive.set(-calcSpeedForGyroscopeTurningRightDrive(delta));
			}
		}
	}

	/*
	 * public double calcSpeedForGoingStraightLeftDrive(double currentAngle) {
	 * return clamp(Math.tan(Math.toRadians(currentAngle)) / 2 + 0.3, -0.35,
	 * 0.35); }
	 * 
	 * public double calcSpeedForGoingStraightRightDrive(double currentAngle) {
	 * return calcSpeedForGoingStraightLeftDrive(-currentAngle); }
	 */

	public void driving(double desiredDistance) throws AutoEndException {
		double startingAngle = c.gyro.getAngle();
		c.rightDriveEncoder.reset();
		while (c.isAuto()) {
			double currentAngle = c.gyro.getAngle();
			double deltaAngle = startingAngle - currentAngle;
			double currentDistance = c.rightDriveEncoder.getDistance();

			if (currentDistance <= -desiredDistance) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				break;
			} else {
				// Positive for left drive and negative for right drive will
				// make the robot go forward.
				// c.leftDrive.set(calcSpeedForGoingStraightLeftDrive(deltaAngle));
				// c.rightDrive.set(-calcSpeedForGoingStraightRightDrive(deltaAngle));
				c.leftDrive.set(CalcSpeed.calcSpeedForDrivingStraight(deltaAngle, CalcSpeed.LEFT));
				c.rightDrive.set(CalcSpeed.calcSpeedForDrivingStraight(deltaAngle, CalcSpeed.RIGHT));
			}
		}
	}

	public void drivingOverObsticals(double desiredDistance) throws AutoEndException {
		c.rightDriveEncoder.reset();
		while (c.isAuto()) {
			double currentDistance = c.rightDriveEncoder.getDistance();

			if (currentDistance <= -desiredDistance) {
				c.rightDrive.set(0);
				c.leftDrive.set(0);
				break;
			} else {
				c.leftDrive.set(0.75);
				c.rightDrive.set(-0.75);
			}
		}
	}

	/*
	 * public double calcSpeedForVisionTurnLeftDrive(double azimuth) { return
	 * clamp(Math.tan(Math.toRadians(azimuth)) / 1, -0.15, 0.15); }
	 * 
	 * public double calcSpeedForVisionTurnRightDrive(double azimuth) { return
	 * calcSpeedForVisionTurnLeftDrive(-azimuth); }
	 */

	public boolean isVisionAiming = false;

	public void visionAiming() throws AutoEndException {
		isVisionAiming = true;
		while (c.isAuto()) {
			vd.updateVisionData();
			vd.autoVisionTrackingRunningCheck();
			findTarget(vd);

			if (vd.azimuth >= Defines.MAX_AZIMUTH || vd.azimuth <= Defines.MIN_AZIMUTH) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				isVisionAiming = false;
				break;
			} else {
				// c.leftDrive.set(calcSpeedForVisionTurnLeftDrive(vd.azimuth));
				// c.rightDrive.set(-calcSpeedForVisionTurnRightDrive(vd.azimuth));
				c.leftDrive.set(CalcSpeed.calcSpeedForVisionTurning(vd.azimuth, CalcSpeed.LEFT));
				c.rightDrive.set(CalcSpeed.RIGHT * CalcSpeed.calcSpeedForVisionTurning(vd.azimuth, CalcSpeed.RIGHT));
			}
		}
	}

	/*
	 * public double calcSpeedFromDistance(double visionDistance) { return
	 * clamp((Math.atan(visionDistance / 3)) / 3, -0.4, 0.4); }
	 */

	public void visionDriving() throws AutoEndException {
		while (c.isAuto()) {
			vd.updateVisionData();
			vd.autoVisionTrackingRunningCheck();
			findTarget(vd);

			double delta = (Math.abs(vd.visionDistance - Defines.SHOOTER_RANGE));
			double alpha = (vd.visionDistance - Defines.SHOOTER_RANGE);

			if (Math.abs(delta) < 4) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				break;
			} else {
				// c.leftDrive.set(calcSpeedFromDistance(alpha));
				// c.rightDrive.set(-calcSpeedFromDistance(alpha));
				c.leftDrive.set(CalcSpeed.calcSpeedForVisionDriving(alpha, CalcSpeed.LEFT));
				c.rightDrive.set(CalcSpeed.RIGHT * CalcSpeed.calcSpeedForVisionDriving(alpha, CalcSpeed.RIGHT));
			}
		}
	}

	private void findTarget(VisionData vd) throws AutoEndException {
		while (c.isAuto()) {
			vd.autoVisionTrackingRunningCheck();
			if (!vd.isUsable()) {
				vd.updateVisionData();
				c.leftDrive.set(0.3);
				c.rightDrive.set(0.3);

			} else {
				if (!isVisionAiming) {
					visionAiming();
				}
				break;
			}
		}
	}

	public void gyroCentering() throws AutoEndException {
		while (c.isAuto()) {
			gyroTurning(-c.gyro.getAngle());
			break;
		}
	}

	public void angleShooter() throws AutoEndException {
		double startTime = c.time.get();
		while (c.isAuto()) {
			double shootingTime = .75;

			double deltaTime = shootingTime - startTime;

			c.shooterAngleMotor.set(-0.33);

			if (deltaTime >= shootingTime) {
				c.shooterAngleMotor.set(0);
				break;
			}
		}
	}

	public void shoot() throws AutoEndException {
		double startTime = c.time.get();
		while (c.isAuto()) {
			double warmUpShooterMotorPeriod = 0.5;
			double pushingBallPeriod = 1;

			double currentTime = c.time.get();
			double deltaTime = currentTime - startTime;

			if (deltaTime < warmUpShooterMotorPeriod) {
				c.leftShooterMotor.set(Defines.MOTOR_FULL_SPEED_FORWARD);
				c.rightShooterMotor.set(Defines.MOTOR_FULL_SPEED_REVERSE);
			} else if (deltaTime > warmUpShooterMotorPeriod && deltaTime < pushingBallPeriod) {
				c.leftShooterMotor.set(Defines.MOTOR_FULL_SPEED_FORWARD);
				c.rightShooterMotor.set(Defines.MOTOR_FULL_SPEED_REVERSE);
				c.shooterBallPusherMotor.set(Relay.Value.kForward);
			} else {
				c.leftShooterMotor.set(Defines.MOTOR_OFF);
				c.rightShooterMotor.set(Defines.MOTOR_OFF);
				c.shooterBallPusherMotor.set(Relay.Value.kOff);
				break;
			}
		}
	}

	public abstract void run() throws AutoEndException;
}
