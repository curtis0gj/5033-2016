package org.usfirst.frc.team5033.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Joystick;

public class Controls {
	Joystick joystick = new Joystick(Defines.JOYSTICK_CHANNEL);
	Joystick xbox = new Joystick(Defines.XBOX_CHANNEL);

	public Controls() {
	}

	public double joystickX() {
		return joystick.getX();
	}

	public double joystickY() {
		return joystick.getY();
	}

	public double xboxLeftTrigger() {
		return xbox.getRawAxis(Defines.XBOX_LEFT_TRIGGER);
	}

	public double xboxLeftYAxis() {
		return xbox.getRawAxis(Defines.XBOX_LEFT_Y_AXIS);
	}

	public double xboxRightYAxis() {
		return xbox.getRawAxis(Defines.XBOX_RIGHT_Y_AXIS);
	}

	public boolean joystickTrigger() {
		return joystick.getRawButton(Defines.JOYSTICK_TRIGGER);
	}

	public BooleanSupplier xboxXButton() {
		return () -> xbox.getRawButton(Defines.XBOX_X_BUTTON);
	}

	public BooleanSupplier xboxBButton() {
		return () -> xbox.getRawButton(Defines.XBOX_B_BUTTON);
	}

	public BooleanSupplier xboxYButton() {
		return () -> xbox.getRawButton(Defines.XBOX_Y_BUTTON);
	}

	public BooleanSupplier xboxAButton() {
		return () -> xbox.getRawButton(Defines.XBOX_A_BUTTON);
	}
}
