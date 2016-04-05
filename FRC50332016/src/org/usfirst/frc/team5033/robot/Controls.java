package org.usfirst.frc.team5033.robot;

public class Controls {
	Components c;

	public Controls(Components c) {
		this.c = c;
	}

	public double joystickX() {
		return c.joystick.getX();
	}

	public double joystickY() {
		return c.joystick.getY();
	}

	public double xboxLeftYAxis() {
		return c.xbox.getRawAxis(Defines.XBOX_LEFT_Y_AXIS);
	}

	public double xboxRightYAxis() {
		return c.xbox.getRawAxis(Defines.XBOX_RIGHT_Y_AXIS);
	}

	public boolean joystickTrigger() {
		return c.joystick.getRawButton(Defines.JOYSTICK_TRIGGER);
	}

	public boolean xboxXButton() {
		return c.xbox.getRawButton(Defines.XBOX_X_BUTTON);
	}

	public boolean xboxBButton() {
		return c.xbox.getRawButton(Defines.XBOX_B_BUTTON);
	}

	public boolean xboxYButton() {
		return c.xbox.getRawButton(Defines.XBOX_Y_BUTTON);
	}

	public boolean xboxAButton() {
		return c.xbox.getRawButton(Defines.XBOX_A_BUTTON);
	}
}
