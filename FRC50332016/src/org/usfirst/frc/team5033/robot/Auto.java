package org.usfirst.frc.team5033.robot;

import org.usfirst.frc.team5033.robot.Defines.AutonomousRoutines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	AutoMethods auto;

	public void initializeSmartDashBoard(Components c) {
		c.chooser.addDefault("DO NOTHING (DEFAULT)", Defines.AutonomousRoutines.DO_NOTHING);
		c.chooser.addObject("LOW BAR SHOOT", Defines.AutonomousRoutines.LOW_BAR_SHOOT);
		c.chooser.addObject("CENTER DEFENSE SHOOT", Defines.AutonomousRoutines.CENTER_DEFENSE_SHOOT);
		c.chooser.addObject("TOP DEFENSE SHOOT", Defines.AutonomousRoutines.TOP_DEFENSE_SHOOT);
		SmartDashboard.putData("AUTONOMOUS MODES", c.chooser);
	}

	public void run(AutonomousRoutines routines, Components c) {
		switch (routines) {
		case DO_NOTHING:
			auto = new DoNothing(c);
			break;

		case LOW_BAR_SHOOT:
			auto = new LowBarShootAuto(c);
			break;

		case CENTER_DEFENSE_SHOOT:
			auto = new CenterDefenseShoot(c);
			break;

		case TOP_DEFENSE_SHOOT:
			auto = new TopDefenseShoot(c);
			break;
		}
		auto.run();
	}
}
