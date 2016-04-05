package org.usfirst.frc.team5033.robot;

public class LowBarAuto extends AutoMethods {

	public LowBarAuto(Components c) {
		super(c);
	}

	public void run() throws AutoEndException {
		driving(121.61);
		gyroCentering();
		driving(120.25);
	}
}
