package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutoMethods {
	Components c;

	public AutoMethods(Components c) {
		this.c = c;
	}

	/**
	 * @description A clamp.
	 * @param num
	 * @param low
	 * @param high
	 * @return
	 */
	public double clamp(double num, double low, double high) {
		return Math.max(low, Math.min(num, high));
	}

	/**
	 * @description timeDelay method in case we need to delay.
	 * @param desiredTime
	 */
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

	/**
	 * @description Method and functions for turning a certain angle with the
	 *              gyroscope. - 90 for for a desiredAngle is left and + 90 for
	 *              a desiredAngle is right.
	 * @param currentAngle
	 * @return
	 */
	public double calcSpeedForGyroscopeTurningLeftDrive(double currentAngle) {
		return clamp(Math.tan(Math.toRadians(currentAngle)) / 1.40, -0.35, 0.35);
	}

	public double calcSpeedForGyroscopeTurningRightDrive(double currentAngle) {
		return calcSpeedForGyroscopeTurningLeftDrive(-currentAngle);
	}

	public void gyroTurning(double desiredAngle) {
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

	/**
	 * @description Method and functions for driving straight for a certain
	 *              distance with the gyroscope and encoder. Side note negative
	 *              speed values for right drive and positive for left // will
	 *              drive the robot forward.
	 * @param desiredDistance
	 * @return
	 */
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
			double deltaAngle = -(currentAngle - startingAngle);
			double currentDistance = c.rightDriveEncoder.getDistance();

			if (currentDistance <= -desiredDistance) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				break;
			} else {
				c.leftDrive.set(calcSpeedForGoingStraightLeftDrive(deltaAngle));
				c.rightDrive.set(-calcSpeedForGoingStraightRightDrive(deltaAngle));
			}
		}
	}

	/**
	 * @description This method drives at a constant speed and implements the
	 *              encoder to drive for a certain distance. The purpose is to
	 *              drive over obstacles. 160 for desiredDistance worked when
	 *              the robot was 12 inches from the obstacle.
	 * @param desiredDistance
	 */
	public void drivingOverObsticals(double desiredDistance) {
		c.rightDriveEncoder.reset();
		while (c.isAuto()) {
			double currentDistance = c.rightDriveEncoder.getDistance();

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

	/**
	 * @description Method and functions for aiming using the azimuth.
	 * @param azimuth
	 * @return
	 */
	public double calcSpeedForVisionTurnLeftDrive(double azimuth) {
		return clamp(Math.tan(Math.toRadians(azimuth)) / 1, -0.25, 0.25);
	}

	public double calcSpeedForVisionTurnRightDrive(double azimuth) {
		return calcSpeedForVisionTurnLeftDrive(-azimuth);
	}

	public void visionAiming() {
		while (c.isAuto()) {
			double azimuth = SmartDashboard.getNumber("azimuth");

			if (azimuth >= 355 || azimuth <= 5) {
				c.leftDrive.set(0);
				c.rightDrive.set(0);
				break;
			} else {
				c.leftDrive.set(calcSpeedForVisionTurnLeftDrive(azimuth));
				c.rightDrive.set(-calcSpeedForVisionTurnRightDrive(azimuth));
			}
		}
	}

	/**
	 * @description Method and functions for driving to the target using the
	 *              vision tracking distance. The objective is to stop just
	 *              before the target to shoot.
	 * @param visionDistance
	 * @return
	 */
	public double calcSpeedFromDistance(double visionDistance) {
		return clamp((Math.atan(visionDistance / 3)) / 3, -0.4, 0.4);
	}

	public void visionDriving() {
		while (c.isAuto()) {
			String visionData = SmartDashboard.getString("distance and azimuth");

			String[] distanceAndAzimuth = visionData.split(":", 2);

			if (distanceAndAzimuth.length == 0) {
				System.out.println("empty array");
				// What else should I do here?
			}

			double visionDistance = Double.parseDouble(distanceAndAzimuth[0]);
			double azimuth = Double.parseDouble(distanceAndAzimuth[1]);

			double delta = (Math.abs(visionDistance - Defines.SHOOTER_RANGE));
			double alpha = (visionDistance - Defines.SHOOTER_RANGE);

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

	/**
	 * @description Method for centering the robot using the gyroscope.
	 * @param currentAngle
	 * @return
	 */
	public void gyroCentering() {
		while (c.isAuto()) {
			gyroTurning(-c.gyro.getAngle());
		}
	}

	/**
	 * @description Method for changing the angle of the shooter.
	 * @param desiredAngle
	 */
	public void angleShooter(double desiredAngle) {
		while (c.isAuto()) {
			c.shooterAngleMotor.set(Relay.Value.kForward);
			break;
		}
	}

	/**
	 * @description Method for shooting for a certain time.
	 * @param shootingTime
	 */
	public void shoot(double shootingTime) {
		double startTime = c.time.get();
		while (c.isAuto()) {
			double currentTime = c.time.get();
			double deltaTime = currentTime - startTime;

			if (deltaTime < shootingTime) {
				c.leftShooterMotor.set(Defines.LEFT_SHOOT_SPEED);
				c.rightShooterMotor.set(Defines.RIGHT_SHOOT_SPEED);
			} else {
				c.leftShooterMotor.set(Defines.SHOOTER_OFF);
				c.rightShooterMotor.set(Defines.SHOOTER_OFF);
				break;
			}
		}
	}

	public abstract void run();
}
