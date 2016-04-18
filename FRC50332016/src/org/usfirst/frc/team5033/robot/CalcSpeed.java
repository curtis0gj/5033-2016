package org.usfirst.frc.team5033.robot;

public class CalcSpeed {
	static int RIGHT = -1;
	static int LEFT = 1;

	public static double clamp(double num, double low, double high) {
		return Math.max(low, Math.min(num, high));
	}

	public static double calcSpeedForGyroscopeTurning(double currentAngle, int direction) {
		return clamp(Math.tan(Math.toRadians(currentAngle * direction)) / 0.7, -0.35, 0.35);
	}

	public static double calcSpeedForDrivingStraight(double currentAngle, int direction) {
		return clamp(Math.tan(Math.toRadians(currentAngle * direction)) / 2 + 0.3, -0.35, 0.35);
	}

	public static double calcSpeedForVisionTurning(double azimuth, int direction) {
		return clamp(Math.tan(Math.toRadians(azimuth * direction)) / 1, -0.15, 0.15);
	}

	public static double calcSpeedForVisionDriving(double visionDistance, int direction) {
		return clamp((Math.atan(visionDistance / 3 * direction)) / 3, -0.4, 0.4);
	}

}
