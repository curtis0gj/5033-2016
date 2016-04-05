package org.usfirst.frc.team5033.robot;

import org.usfirst.frc.team5033.robot.Defines.AutonomousRoutines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	AutoMethods auto;

	public void initializeSmartDashBoard(Components c) {
		c.chooser.addDefault("DO NOTHING (DEFAULT)", Defines.AutonomousRoutines.DO_NOTHING_AUTO);
		c.chooser.addObject("LOW BAR", Defines.AutonomousRoutines.LOW_BAR_AUTO);
		c.chooser.addObject("DEFENSE", Defines.AutonomousRoutines.DEFENSES_AUTO);
		c.chooser.addObject("EXTRA AUTO 1", Defines.AutonomousRoutines.EXTRA_AUTO_1);
		c.chooser.addObject("EXTRA AUTO 2", Defines.AutonomousRoutines.EXTRA_AUTO_2);
		c.chooser.addObject("EXTRA AUTO 3", Defines.AutonomousRoutines.EXTRA_AUTO_3);
		SmartDashboard.putData("AUTONOMOUS MODES", c.chooser);
	}

	public void run(AutonomousRoutines routines, Components c) throws AutoEndException {
		switch (routines) {
		case DO_NOTHING_AUTO:
			auto = new DoNothingAuto(c);
			break;

		case LOW_BAR_AUTO:
			auto = new LowBarAuto(c);
			break;

		case DEFENSES_AUTO:
			auto = new DefensesAuto(c);
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
