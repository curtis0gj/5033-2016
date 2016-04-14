package org.usfirst.frc.team5033.robot;

public class Functions {

	public Functions() {
		// TODO Auto-generated constructor stub
	}
	// Right needs to be negative.

	public static final int LEFT = 1;
	public static final int RIGHT = -1;

	public static double clamp(double num, double low, double high) {
		return Math.max(low, Math.min(num, high));
	}

	public static double calcSpeedForGyroscopeTurning(double currentAngle) {
		return clamp(Math.tan(Math.toRadians(currentAngle)) / 0.7, -0.35, 0.35);
	}

	public static double calcSpeedForDrivingStraight(double currentAngle) {
		return clamp(Math.tan(Math.toRadians(currentAngle)) / 2 + 0.3, -0.35, 0.35);
	}

	public static double calcSpeedForVisionTurning(double azimuth) {
		return clamp(Math.tan(Math.toRadians(azimuth)) / 1, -0.15, 0.15);
	}

	public static double calcSpeedForVisionDriving(double visionDistance) {
		return clamp((Math.atan(visionDistance / 3)) / 3, -0.4, 0.4);
	}

}
