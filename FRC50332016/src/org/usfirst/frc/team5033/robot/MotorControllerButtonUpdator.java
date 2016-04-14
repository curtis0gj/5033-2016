package org.usfirst.frc.team5033.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Talon;

public class MotorControllerButtonUpdator {
	Talon talon;
	BooleanSupplier forward;
	BooleanSupplier backward;

	public MotorControllerButtonUpdator(Talon talon, BooleanSupplier forward, BooleanSupplier backward) {
		this.talon = talon;
		this.forward = forward;
		this.backward = backward;
	}

	public void update() {
		if (forward.getAsBoolean()) {
			talon.set(Defines.LIFT_OBSTICAL_SPEED);
		} else if (backward.getAsBoolean()) {
			talon.set(Defines.LOWER_OBSTICAL_SPEED);
		} else {
			talon.set(Defines.OBSTICAL_OFF);
		}
	}
}
