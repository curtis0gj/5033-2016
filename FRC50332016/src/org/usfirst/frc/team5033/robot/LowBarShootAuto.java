package org.usfirst.frc.team5033.robot;

public class LowBarShootAuto extends AutoMethods {

	public LowBarShootAuto(Components c) {
		super(c);
	}

	public void run() {
		driving(122);
		gyroCentering();
		driving(120);
		gyroTurning(45);
		visionAiming();
		visionDriving();
		angleShooter(45);
		shoot(3);
	}
}
