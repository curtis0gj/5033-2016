package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionData {

	public VisionData() {

	}

	public String[] visionArray() {
		String smartDashBoardVisionData = SmartDashboard.getString("distance and azimuth");
		String[] distanceAndAzimuth = smartDashBoardVisionData.split(":", 2);
		return distanceAndAzimuth;
	}

	public double visionDistance() {
		double visionDistance = Double.parseDouble(visionArray()[0]);
		return visionDistance;
	}

	public double azimuth() {
		double azimuth = Double.parseDouble(visionArray()[1]);
		return azimuth;
	}
}
