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
	NetworkTable table;
	Controls control = new Controls(this);
	SendableChooser chooser = new SendableChooser();
	Joystick joystick = new Joystick(Defines.JOYSTICK_CHANNEL);
	Joystick xbox = new Joystick(Defines.XBOX_CHANNEL);
	Talon leftDrive = new Talon(Defines.LEFT_DRIVE_CHANNEL);
	Talon rightDrive = new Talon(Defines.RIGHT_DRIVE_CHANNEL);
	AnalogGyro gyro = new AnalogGyro(Defines.ANALOG_GYROSCOPE_CHANNEL);
	Talon shooterAngleMotor = new Talon(Defines.SHOOTER_ANGLE_MOTOR_CHANNEL);
	Relay shooterBallPusherMotor = new Relay(Defines.SHOOTER_BALL_PUSHER_MOTOR_CHANNEL);
	Talon obsticalLiftingMotor = new Talon(Defines.OBSTICAL_LIFTING_MOTOR_CHANNEL);
	TalonSRX leftShooterMotor = new TalonSRX(Defines.LEFT_SHOOTER_CHANNEL);
	TalonSRX rightShooterMotor = new TalonSRX(Defines.RIGHT_SHOOTER_CHANNEL);
	Encoder rightDriveEncoder = new Encoder(Defines.RIGHT_DRIVE_ENCODER_CHANNEL_A,
			Defines.RIGHT_DRIVE_ENCODER_CHANNEL_B, true, EncodingType.k4X);
	Timer time = new Timer();
	Auto auto = new Auto();
	RobotDrive tankDrive = new RobotDrive(leftDrive, rightDrive);

	public Components(BooleanSupplier robotStateCheck) {
		isAutoCheck = robotStateCheck;
		table = NetworkTable.getTable("SmartDashboard");
		auto.initializeSmartDashBoard(this);
	}

	public boolean isAuto() throws AutoEndException {
		if (!isAutoCheck.getAsBoolean()) {
			throw new AutoEndException();
		}
		return isAutoCheck.getAsBoolean();
	}

	public void resetAll() {
		leftShooterMotor.set(0);
		rightShooterMotor.set(0);
		shooterBallPusherMotor.set(Relay.Value.kOff);
		obsticalLiftingMotor.set(0);
		shooterAngleMotor.set(0);
		leftDrive.set(0);
		rightDrive.set(0);
	}
}
