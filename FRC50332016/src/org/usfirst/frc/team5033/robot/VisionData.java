package org.usfirst.frc.team5033.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionData {
	public String smartDashBoardVisionData;
	public String[] distanceAndAzimuth;
	public double visionDistance;
	public double azimuth;
	public boolean lost;
	Components c;

	public VisionData() {
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
		if (distanceAndAzimuth.toString() == "3.14:-1") {
			while (c.isAuto()) {
				updateVisionData();
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
	}

	public void visionTrackingRunningCheck() {
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
}
