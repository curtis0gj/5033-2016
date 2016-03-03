package org.usfirst.frc.team5033.robot;

public class TopDefenseShoot extends AutoMethods {

	public TopDefenseShoot(Components c) {
		super(c);
	}

	public void run() {
		drivingOverObsticals(160);
		gyroCentering();
		driving(120);
		gyroTurning(45);
		visionAiming();
		visionDriving();
		angleShooter(45);
		shoot(3);
	}
}
