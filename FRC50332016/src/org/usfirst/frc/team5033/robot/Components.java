package org.usfirst.frc.team5033.robot;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Components {
	public BooleanSupplier isAutoCheck;
	RobotDrive tankDrive;
	NetworkTable table;
	SendableChooser chooser = new SendableChooser();
	Joystick joystick = new Joystick(Defines.JOYSTICK_CHANNEL);
	Joystick xbox = new Joystick(Defines.XBOX_CHANNEL);
	Talon leftDrive = new Talon(Defines.LEFT_DRIVE_CHANNEL);
	Talon rightDrive = new Talon(Defines.RIGHT_DRIVE_CHANNEL);
	AnalogGyro gyro = new AnalogGyro(Defines.ANALOG_GYROSCOPE_CHANNEL);
	Relay shooterAngleMotor = new Relay(Defines.SHOOTER_ANGLE_MOTOR_CHANNEL);
	Relay shooterBallPusherMotor = new Relay(3);
	TalonSRX leftShooterMotor = new TalonSRX(Defines.LEFT_SHOOTER_CHANNEL);
	TalonSRX rightShooterMotor = new TalonSRX(Defines.RIGHT_SHOOTER_CHANNEL);
	Encoder rightDriveEncoder = new Encoder(Defines.RIGHT_DRIVE_ENCODER_CHANNEL_A,
			Defines.RIGHT_DRIVE_ENCODER_CHANNEL_B, true, EncodingType.k4X);
	Encoder shooterAngleEncoder = new Encoder(9, 8, true, EncodingType.k4X);
	Timer time = new Timer();
	Auto auto = new Auto();

	public Components(BooleanSupplier robotStateCheck) {
		isAutoCheck = robotStateCheck;
		table = NetworkTable.getTable("SmartDashboard");
		auto.initializeSmartDashBoard(this);
	}

	public boolean isAuto() {
		if (!isAutoCheck.getAsBoolean()) {
			throw new RuntimeException("isAuto is false");
		}
		return isAutoCheck.getAsBoolean();
	}
}
