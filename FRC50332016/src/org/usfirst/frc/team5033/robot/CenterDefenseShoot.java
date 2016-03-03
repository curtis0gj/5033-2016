package org.usfirst.frc.team5033.robot;

public class CenterDefenseShoot extends AutoMethods {

	public CenterDefenseShoot(Components c) {
		super(c);
	}

	public void run() {
		drivingOverObsticals(160);
		gyroCentering();
		visionDriving();
		angleShooter(45);
		shoot(3);
	}
}
