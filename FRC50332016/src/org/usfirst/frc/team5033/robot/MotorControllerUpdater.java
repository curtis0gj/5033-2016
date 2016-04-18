package org.usfirst.frc.team5033.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class MotorControllerUpdater {
	Talon talon;
	TalonSRX talonsrx;
	TalonSRX talonsrx2;

	DoubleSupplier shootAxis;
	BooleanSupplier forward;
	BooleanSupplier backward;

	public MotorControllerUpdater(Talon talon, TalonSRX talonsrx, TalonSRX talonsrx2, BooleanSupplier forward,
			BooleanSupplier backward, DoubleSupplier shootAxis) {
		this.talon = talon;
		this.talonsrx = talonsrx;
		this.talonsrx2 = talonsrx;
		this.forward = forward;
		this.backward = backward;
		this.shootAxis = shootAxis;
	}

	public void update() {
		if (forward.getAsBoolean()) {
			talon.set(Defines.MOTOR_FULL_SPEED_FORWARD);
		} else if (backward.getAsBoolean()) {
			talon.set(Defines.MOTOR_FULL_SPEED_REVERSE);
		} else {
			talon.set(Defines.MOTOR_OFF);
		}
	}

	public void update2() {
		if (shootAxis.getAsDouble() < Defines.MINIMUM_AXIS_VALUE) {
			talonsrx.set(Defines.MOTOR_FULL_SPEED_FORWARD);
			talonsrx2.set(Defines.MOTOR_FULL_SPEED_REVERSE);
		} else if (shootAxis.getAsDouble() > Defines.MAXIMUM_AXIS_VALUE) {
			talonsrx.set(Defines.MOTOR_HALF_SPEED_REVERSE);
			talonsrx2.set(Defines.MOTOR_HALF_SPEED_FORWARD);
		} else {
			talonsrx.set(Defines.MOTOR_OFF);
			talonsrx2.set(Defines.MOTOR_OFF);
		}
		/*
		 * if (control.xboxLeftYAxis() < Defines.MINIMUM_AXIS_VALUE) {
		 * c.leftShooterMotor.set(Defines.MOTOR_FULL_SPEED_FORWARD);
		 * c.rightShooterMotor.set(Defines.MOTOR_FULL_SPEED_REVERSE); } else if
		 * (control.xboxLeftYAxis() > Defines.MAXIMUM_AXIS_VALUE) {
		 * c.leftShooterMotor.set(Defines.MOTOR_HALF_SPEED_REVERSE);
		 * c.rightShooterMotor.set(Defines.MOTOR_HALF_SPEED_FORWARD); } else {
		 * c.leftShooterMotor.set(Defines.MOTOR_OFF);
		 * c.rightShooterMotor.set(Defines.MOTOR_OFF); }
		 */
	}
}
