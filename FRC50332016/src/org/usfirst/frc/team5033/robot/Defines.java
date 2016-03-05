package org.usfirst.frc.team5033.robot;

public class Defines {
	// PWM channel for the first and second drive train Talon.
	public static final int LEFT_DRIVE_CHANNEL = 0;
	// PWM channel for the third and fourth drive train Talon.
	public static final int RIGHT_DRIVE_CHANNEL = 1;

	// PWM channel for the left shooters TalonSRX.
	public static final int LEFT_SHOOTER_CHANNEL = 9;
	// PWM channel for the right shooters TalonSRX.
	public static final int RIGHT_SHOOTER_CHANNEL = 8;
	// Relay channel that controls the motor for angling the shooter.
	public static final int SHOOTER_ANGLE_MOTOR_CHANNEL = 0;

	// USB channel for the driving joy stick.
	public static final int JOYSTICK_CHANNEL = 0;
	// USB channel for the mechanism Xbox remote.
	public static final int XBOX_CHANNEL = 1;

	// Analog channel for the gyroscope.
	public static final int ANALOG_GYROSCOPE_CHANNEL = 0;

	// Digital channel for the right drive train encoder.
	public static final int RIGHT_DRIVE_ENCODER_CHANNEL_A = 0;
	// Digital channel for the right drive train encoder.
	public static final int RIGHT_DRIVE_ENCODER_CHANNEL_B = 1;

	// Digital channel for the right drive train encoder.
	public static final int LEFT_DRIVE_ENCODER_CHANNEL_A = 2;
	// Digital channel for the right drive train encoder.
	public static final int LEFT_DRIVE_ENCODER_CHANNEL_B = 3;

	public static final int XBOX_LEFT_Y_AXIS = 1;
	public static final int XBOX_RIGHT_Y_AXIS = 5;
	public static final double MINIMUM_Y_AXIS = -0.5;
	public static final double MAXIMUM_Y_AXIS = 0.5;

	public static final double SHOOTER_RANGE = 40;
	public static final double LEFT_SHOOT_SPEED = -1.00;
	public static final double RIGHT_SHOOT_SPEED = 1.00;
	public static final double LEFT_IMPELL_SPEED = 0.50;
	public static final double RIGHT_IMPELL_SPEED = -0.50;
	public static final double SHOOTER_OFF = 0.00;

	public static final double WHEEL_DIAMETER = 7.65;
	public static final double PULSE_PER_REVOLUTION = 360;
	public static final double ENCODER_GEAR_RATIO = 1;
	public static final double GEAR_RATIO = 1 / 1.667;
	public static final double FUDGE_FACTOR = 1.2;

	public enum AutonomousRoutines {
		DO_NOTHING,
		LOW_BAR_BOTTOM_GOAL_SHOOT,
		CENTER_GOAL_SHOOT,
		EXTRA_AUTO_1,
		EXTRA_AUTO_2,
		EXTRA_AUTO_3
	}
}