package org.usfirst.frc.team5033.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Relay;

public class RelayUpdater {
	Relay relay;
	BooleanSupplier forward;
	BooleanSupplier backward;

	public RelayUpdater(Relay relay, BooleanSupplier forward, BooleanSupplier backward) {
		this.relay = relay;
		this.forward = forward;
		this.backward = backward;
	}

	public void update() {
		if (forward.getAsBoolean()) {
			relay.set(Relay.Value.kForward);
		} else if (backward.getAsBoolean()) {
			relay.set(Relay.Value.kReverse);
		} else {
			relay.set(Relay.Value.kOff);
		}
	}
}
