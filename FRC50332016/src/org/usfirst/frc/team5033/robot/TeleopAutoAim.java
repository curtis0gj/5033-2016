package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopAutoAim {
	Components c;
	VisionData vd;

	public TeleopAutoAim(Components c) {
		this.c = c;
		vd = new VisionData(c);
	}

	public double clamp(double num, double low, double high) {
		return Math.max(low, Math.min(num, high));
	}

	public double calcSpeedForGyroscopeTurningLeftDrive(double currentAngle) {
		return clamp(Math.tan(Math.toRadians(currentAngle)) / 0.7, -0.35, 0.35);
	}

	public double calcSpeedForVisionTurnLeftDrive(double azimuth) {
		return clamp(Math.tan(Math.toRadians(azimuth)) / 1, -0.15, 0.15);
	}

	public double calcSpeedForVisionTurnRightDrive(double azimuth) {
		return calcSpeedForVisionTurnLeftDrive(-azimuth);
	}

	public void azimuthAiming() {
		vd.updateVisionData();

		if (vd.azimuth >= Defines.MAX_AZIMUTH || vd.azimuth <= Defines.MIN_AZIMUTH) {
			c.leftDrive.set(0);
			c.rightDrive.set(0);
			return;
		} else {
			c.leftDrive.set(calcSpeedForVisionTurnLeftDrive(vd.azimuth));
			c.rightDrive.set(-calcSpeedForVisionTurnRightDrive(vd.azimuth));
		}
	}

	public void indicators() {
		vd.updateVisionData();

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
	}
}
