package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionData {
	public String smartDashBoardVisionData;
	public String[] distanceAndAzimuth;
	public double visionDistance;
	public double azimuth;
	Components c;

	public VisionData(Components c) {
		updateVisionData();
	}

	public void updateVisionData() {
		smartDashBoardVisionData = SmartDashboard.getString("distance and azimuth");
		distanceAndAzimuth = smartDashBoardVisionData.split(":", 2);

		try {
			visionDistance = Double.parseDouble(distanceAndAzimuth[0]);
			azimuth = Double.parseDouble(distanceAndAzimuth[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void targetLost() {
		// How do I do this with one if statement.
		if (distanceAndAzimuth.toString() == "3.14:-1") {
			while (c.isAuto()) {
				updateVisionData();
				if (distanceAndAzimuth.toString() != "3.14:-1") {
					break;
				}
			}
		}
	}

	public void visionTrackingRunningCheck() {
		// How do I do this with one if statement.
		if (distanceAndAzimuth.length <= 1) {
			while (c.isAuto()) {
				updateVisionData();
				if (distanceAndAzimuth.length > 1) {
					break;
				}
			}
			return;
		}
	}

	public boolean isUsable() {
		return distanceAndAzimuth.length > 1 && distanceAndAzimuth.toString() != "3.14:-1";
	}
}
