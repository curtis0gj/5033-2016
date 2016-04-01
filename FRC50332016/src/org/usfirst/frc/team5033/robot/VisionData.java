package org.usfirst.frc.team5033.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionData {
	public String smartDashBoardVisionData;
	public String[] distanceAndAzimuth = new String[0];
	public double visionDistance;
	public double azimuth;
	Components c;

	public VisionData(Components c) {
		this.c = c;
		updateVisionData();
	}

	public void updateVisionData() {
		try {
			smartDashBoardVisionData = SmartDashboard.getString("distance and azimuth");
			distanceAndAzimuth = smartDashBoardVisionData.split(":", 2);

			visionDistance = Double.parseDouble(distanceAndAzimuth[0]);
			azimuth = Double.parseDouble(distanceAndAzimuth[1]);
		} catch (Exception e) {

		}
	}

	public void targetLost() throws AutoEndException {
		if (distanceAndAzimuth.toString() == "3.14:-1") {
			while (c.isAuto()) {
				updateVisionData();
				if (distanceAndAzimuth.toString() != "3.14:-1") {
					break;
				}
			}
		}
	}

	public void visionTrackingRunningCheck() throws AutoEndException {
		if (distanceAndAzimuth.length <= 1) {
			while (c.isAuto()) {
				updateVisionData();
				if (distanceAndAzimuth.length > 1) {
					break;
				}
			}
		}
	}

	public boolean isUsable() {
		return distanceAndAzimuth.length > 1 && distanceAndAzimuth.toString() != "3.14:-1";
	}
}
