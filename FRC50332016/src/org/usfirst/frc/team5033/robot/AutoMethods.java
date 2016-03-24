package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.Relay;

public abstract class AutoMethods {
	Components c;
	VisionData vd;

	public AutoMethods(Components c) {
		this.c = c;
		vd = new VisionData(c);
	}

	public double clamp(double num, double low, double high) {
		return Math.max(low, Math.min(num, high));
	}

	public void timeDelay(double desiredTime) {
		double startingTime = c.time.get();
		while (c.isAuto()) {
			double currentTime = c.time.get();
			double deltaTime = currentTime - startingTime;
			if (deltaTime >= desiredTime) {
				break;
			}
		}
	}

	public double calcSpeedForGyroscopeTurningLeftDrive(double currentAngle) {
		return clamp(Math.tan(Math.toRadians(currentAngle)) / 1.40, -0.35, 0.35);
	}

	public double calcSpeedForGyroscopeTurningRightDrive(double currentAngle) {
		return calcSpeedForGyroscopeTurningLeftDrive(-currentAngle);
	}

	public void gyroTurning(double desiredAngle) {
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
				c.leftDrive.set(calcSpeedForGyroscopeTurningLeftDrive(delta));
				c.rightDrive.set(-calcSpeedForGyroscopeTurningRightDrive(delta));
			}
		}
	}

	public double calcSpeedForGoingStraightLeftDrive(double currentAngle) {
		return clamp(Math.tan(Math.toRadians(currentAngle)) / 2 + 0.3, -0.35, 0.35);
	}

	public double calcSpeedForGoingStraightRightDrive(double currentAngle) {
		return calcSpeedForGoingStraightLeftDrive(-currentAngle);
	}

	public void driving(double desiredDistance) {
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
				c.leftDrive.set(calcSpeedForGoingStraightLeftDrive(deltaAngle));
				c.rightDrive.set(-calcSpeedForGoingStraightRightDrive(deltaAngle));
			}
		}
	}

	public void drivingOverObsticals(double desiredDistance) {
		c.rightDriveEncoder.reset();
		while (c.isAuto()) {
			double currentDistance = c.rightDriveEncoder.getDistance();

			// 160 for a desired distance worked once when the robot was 12
			// inches from an obstacle.
			if (currentDistance <= -desiredDistance) {
				c.rightDrive.set(0);
				c.leftDrive.set(0);
				break;
			} else {
				c.leftDrive.set(0.5);
				c.rightDrive.set(-0.5);
			}
		}
	}

	public double calcSpeedForVisionTurnLeftDrive(double azimuth) {
		return clamp(Math.tan(Math.toRadians(azimuth)) / 1, -0.25, 0.25);
	}

	public double calcSpeedForVisionTurnRightDrive(double azimuth) {
		return calcSpeedForVisionTurnLeftDrive(-azimuth);
	}

	public boolean isVisionAiming = false;

	public void visionAiming() {
		isVisionAiming = true;
		while (c.isAuto()) {
			vd.updateVisionData();
			vd.visionTrackingRunningCheck();
			findTarget(vd);

			if (vd.azimuth >= Defines.MAX_AZIMUTH || vd.azimuth <= Defines.MIN_AZIMUTH) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				isVisionAiming = false;
				break;
			} else {
				c.leftDrive.set(calcSpeedForVisionTurnLeftDrive(vd.azimuth));
				c.rightDrive.set(-calcSpeedForVisionTurnRightDrive(vd.azimuth));
			}
		}
	}

	public double calcSpeedFromDistance(double visionDistance) {
		return clamp((Math.atan(visionDistance / 3)) / 3, -0.4, 0.4);
	}

	public void visionDriving() {
		while (c.isAuto()) {
			vd.updateVisionData();
			vd.visionTrackingRunningCheck();
			findTarget(vd);

			double delta = (Math.abs(vd.visionDistance - Defines.SHOOTER_RANGE));
			double alpha = (vd.visionDistance - Defines.SHOOTER_RANGE);

			if (Math.abs(delta) < 4) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				break;
			} else {
				c.leftDrive.set(calcSpeedFromDistance(alpha));
				c.rightDrive.set(-calcSpeedFromDistance(alpha));
			}
		}
	}

	private void findTarget(VisionData vd) {
		while (c.isAuto()) {
			vd.visionTrackingRunningCheck();
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

	public void gyroCentering() {
		while (c.isAuto()) {
			gyroTurning(-c.gyro.getAngle());
			break;
		}
	}

	public void angleShooter(double desiredAngle) {
		// Once the shooter is finished then I will do this.
		while (c.isAuto()) {
			double currentShooterAngle = c.shooterAngleEncoder.get();

			c.shooterAngleMotor.set(Relay.Value.kForward);

			if (currentShooterAngle > desiredAngle) {
				c.shooterAngleMotor.set(Relay.Value.kOff);
				break;
			}
		}
	}

	public void shoot() {
		double startTime = c.time.get();
		while (c.isAuto()) {
			double warmUpShooterMotorPeriod = 2;
			double pushingBallPeriod = 4;

			double currentTime = c.time.get();
			double deltaTime = currentTime - startTime;

			if (deltaTime < warmUpShooterMotorPeriod) {
				c.leftShooterMotor.set(Defines.LEFT_SHOOT_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_SHOOT_SPEED);
			} else if (deltaTime > warmUpShooterMotorPeriod && deltaTime < pushingBallPeriod) {
				c.shooterBallPusherMotor.set(Relay.Value.kForward);
			} else {
				c.leftShooterMotor.set(Defines.SHOOTER_OFF);
				c.rightShooterMotor.set(Defines.SHOOTER_OFF);
				c.shooterBallPusherMotor.set(Relay.Value.kOff);
				break;
			}
		}
	}

	public abstract void run();
}
