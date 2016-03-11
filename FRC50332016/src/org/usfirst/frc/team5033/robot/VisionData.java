package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionData {
	public String smartDashBoardVisionData;
	public String[] distanceAndAzimuth;
	public double visionDistance;
	public double azimuth;

	public VisionData() {
		smartDashBoardVisionData = SmartDashboard.getString("distance and azimuth");
		distanceAndAzimuth = smartDashBoardVisionData.split(":", 2);

		visionDistance = Double.parseDouble(distanceAndAzimuth[0]);
		azimuth = Double.parseDouble(distanceAndAzimuth[1]);

		if (distanceAndAzimuth.length <= 1) {

		}
		
		if (distanceAndAzimuth.toString() == "3.14:-1") {
			
		}
	}

}
