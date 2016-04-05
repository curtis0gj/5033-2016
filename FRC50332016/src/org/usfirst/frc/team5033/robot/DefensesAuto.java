package org.usfirst.frc.team5033.robot;

public class DefensesAuto extends AutoMethods {

	public DefensesAuto(Components c) {
		super(c);
	}

	public void run() throws AutoEndException {
		drivingOverObsticals(390);
		gyroCentering();
	}
}
