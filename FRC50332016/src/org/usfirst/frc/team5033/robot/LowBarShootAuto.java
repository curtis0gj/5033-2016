package org.usfirst.frc.team5033.robot;

public class LowBarShootAuto extends AutoMethods {

	public LowBarShootAuto(Components c) {
		super(c);
	}

	public void run() throws AutoEndException {
		driving(121.61);
		gyroCentering();
		driving(120.25);
		gyroTurning(45);
		visionAiming();
		visionDriving();
		visionAiming();
		angleShooter();
		timeDelay(1);
		shoot();
	}
}
