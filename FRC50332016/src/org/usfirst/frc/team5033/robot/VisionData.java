package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionData {
	public String smartDashBoardVisionData;
	public String[] distanceAndAzimuth;
	public double visionDistance;
	public double azimuth;
	public boolean lost;
	Components c;

	public VisionData() {
		smartDashBoardVisionData = SmartDashboard.getString("distance and azimuth");
		distanceAndAzimuth = smartDashBoardVisionData.split(":", 2);

		visionDistance = Double.parseDouble(distanceAndAzimuth[0]);
		azimuth = Double.parseDouble(distanceAndAzimuth[1]);
	}

	public void targetLost() {
		if (distanceAndAzimuth.toString() == "3.14:-1") {
			lost = true;
			while (lost) {
				if (distanceAndAzimuth.toString() != "3.14:-1") {
					lost = false;
					break;
				}
			}
			return;
		}
	}

	public void isVisionTrackingRunning() {
		if (distanceAndAzimuth.length <= 1) {
			while (!c.isAuto()) {
				if (distanceAndAzimuth.length > 1) {
					break;
				}
			}
			return;
		}
	}
}
