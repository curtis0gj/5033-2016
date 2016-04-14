package org.usfirst.frc.team5033.robot;

import org.usfirst.frc.team5033.robot.Defines.AutonomousRoutines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	AutoMethods auto;
	Defines.AutonomousRoutines routines;

	public void initializeSmartDashBoard(Components c) {
		c.chooser.addDefault("DO NOTHING (DEFAULT)", Defines.AutonomousRoutines.DO_NOTHING_AUTO);
		c.chooser.addObject("LOW BAR", Defines.AutonomousRoutines.LOW_BAR_AUTO);
		c.chooser.addObject("DEFENSE", Defines.AutonomousRoutines.DEFENSES_AUTO);
		SmartDashboard.putData("AUTONOMOUS MODES", c.chooser);
	}

	public void run(AutonomousRoutines routines, Components c) throws AutoEndException {
		routines = (Defines.AutonomousRoutines) c.chooser.getSelected();

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

		}
		auto.run();
	}
}
