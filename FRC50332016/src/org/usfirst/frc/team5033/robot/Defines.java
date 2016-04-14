package org.usfirst.frc.team5033.robot;

public class Defines {
	// PWM channel.
	public static final int LEFT_DRIVE_CHANNEL = 0;
	// PWM channel.
	public static final int RIGHT_DRIVE_CHANNEL = 1;

	// PWM channel.
	public static final int LEFT_SHOOTER_CHANNEL = 9;
	// PWM channel.
	public static final int RIGHT_SHOOTER_CHANNEL = 8;
	// PWM channel.
	public static final int SHOOTER_ANGLE_MOTOR_CHANNEL = 5;
	// Relay channel.
	public static final int SHOOTER_BALL_PUSHER_MOTOR_CHANNEL = 3;
	// PWM channel.
	public static final int OBSTICAL_LIFTING_MOTOR_CHANNEL = 6;

	public static final int JOYSTICK_CHANNEL = 0;
	public static final int XBOX_CHANNEL = 1;

	// Analog channel.
	public static final int ANALOG_GYROSCOPE_CHANNEL = 0;

	// Digital channel.
	public static final int RIGHT_DRIVE_ENCODER_CHANNEL_A = 0;
	// Digital channel.
	public static final int RIGHT_DRIVE_ENCODER_CHANNEL_B = 1;

	public static final int XBOX_LEFT_Y_AXIS = 1;
	public static final int XBOX_RIGHT_Y_AXIS = 5;
	public static final int XBOX_LEFT_TRIGGER = 3;
	public static final int XBOX_A_BUTTON = 1;
	public static final int XBOX_Y_BUTTON = 4;
	public static final int XBOX_X_BUTTON = 3;
	public static final int XBOX_B_BUTTON = 2;
	public static final int XBOX_BACK_BUTTON = 7;

	public static final double MINIMUM_AXIS_VALUE = -0.5;
	public static final double MAXIMUM_AXIS_VALUE = 0.5;

	public static final int JOYSTICK_TRIGGER = 1;

	public static final double ROBOT_TURNING_ADJUSTMENT = 0.8;

	public static final double SHOOTER_RANGE = 60;
	public static final double SHOOTER_TOLERANCE = 10;
	public static final double MAX_AZIMUTH = 355;
	public static final double MIN_AZIMUTH = 5;
	public static final double LEFT_SHOOT_SPEED = 1.00;
	public static final double RIGHT_SHOOT_SPEED = -1.00;
	public static final double LEFT_IMPELL_SPEED = -0.50;
	public static final double RIGHT_IMPELL_SPEED = 0.50;
	public static final double SHOOTER_OFF = 0.00;
	public static final double LIFT_OBSTICAL_SPEED = 1.00;
	public static final double LOWER_OBSTICAL_SPEED = -1.00;
	public static final double OBSTICAL_OFF = 0.00;

	public static final double WHEEL_DIAMETER = 7.65;
	public static final double PULSE_PER_REVOLUTION = 360;
	public static final double ENCODER_GEAR_RATIO = 1;
	public static final double GEAR_RATIO = 1 / 1.667;
	public static final double FUDGE_FACTOR = 1.2;
	public static final double DISTANCE_PER_PULSE = Math.PI * WHEEL_DIAMETER / PULSE_PER_REVOLUTION / ENCODER_GEAR_RATIO
			/ GEAR_RATIO * FUDGE_FACTOR;

	public enum AutonomousRoutines {
		DO_NOTHING_AUTO,
		LOW_BAR_AUTO,
		DEFENSES_AUTO
	}
}