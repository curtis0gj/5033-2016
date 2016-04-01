package org.usfirst.frc.team5033.robot;

import org.usfirst.frc.team5033.robot.Defines.AutonomousRoutines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	AutoMethods auto;

	public void initializeSmartDashBoard(Components c) {
		c.chooser.addDefault("DO NOTHING (DEFAULT)", Defines.AutonomousRoutines.DO_NOTHING);
		c.chooser.addObject("LOW BAR BOTTOM GOAL SHOOT", Defines.AutonomousRoutines.LOW_BAR_BOTTOM_GOAL_SHOOT);
		c.chooser.addObject("TRAVERSE DEFENSE CENTER GOAL SHOOT", Defines.AutonomousRoutines.CENTER_GOAL_SHOOT);
		c.chooser.addObject("EXTRA AUTO 1", Defines.AutonomousRoutines.EXTRA_AUTO_1);
		c.chooser.addObject("EXTRA AUTO 2", Defines.AutonomousRoutines.EXTRA_AUTO_2);
		c.chooser.addObject("EXTRA AUTO 3", Defines.AutonomousRoutines.EXTRA_AUTO_3);
		SmartDashboard.putData("AUTONOMOUS MODES", c.chooser);
	}

	public void run(AutonomousRoutines routines, Components c) throws AutoEndException {
		switch (routines) {
		case DO_NOTHING:
			auto = new DoNothingAuto(c);
			break;

		case LOW_BAR_BOTTOM_GOAL_SHOOT:
			auto = new LowBarShootAuto(c);
			break;

		case CENTER_GOAL_SHOOT:
			auto = new MiddleGoalShoot(c);
			break;

		case EXTRA_AUTO_1:
			auto = new ExtraAuto1(c);
			break;

		case EXTRA_AUTO_2:
			auto = new ExtraAuto2(c);
			break;

		case EXTRA_AUTO_3:
			auto = new ExtraAuto3(c);
			break;
		}
		auto.run();
	}
}
