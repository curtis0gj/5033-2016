package org.usfirst.frc.team5033.robot;

public class MiddleGoalShoot extends AutoMethods {

	public MiddleGoalShoot(Components c) {
		super(c);
	}

	public void run() {
		drivingOverObsticals(160);
		gyroCentering();
		visionAiming();
		visionDriving();
		visionAiming();
		angleShooter(45);
		shoot(3);
	}
}
