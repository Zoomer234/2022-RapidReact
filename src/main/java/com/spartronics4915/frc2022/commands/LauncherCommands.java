package com.spartronics4915.frc2022.commands;

import com.spartronics4915.frc2022.subsystems.Launcher;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.spartronics4915.frc2022.Constants.Launcher.*;

import com.spartronics4915.frc2022.subsystems.Conveyor;

/**
 * Detailed description of ExampleCommand.
 */
public class LauncherCommands
{
    private final Launcher mLauncher;
    private final Conveyor mConveyor;

    public LauncherCommands(Launcher subsystem, Conveyor conveyor, Joystick mArcadecontroller)
    {
        mLauncher = subsystem;
        mConveyor = conveyor;
        mLauncher.setDefaultCommand(new RunLauncher());
    }

    public class RunLauncher extends CommandBase {
        private double mSpeed = 0.0;

        public RunLauncher() {
            addRequirements(mLauncher);
        }

        @Override
        public void initialize() {
            mSpeed = 0.0;
        }

        @Override
        public void execute() {
            double sliderValue = mLauncher.getToggled() ? mLauncher.getTargetRPS() : 0.0;
            if (mSpeed != sliderValue) {
                mSpeed = sliderValue;
                mLauncher.setMotorSpeed(mSpeed);
            }

            if (!mLauncher.getToggled() && mConveyor.isFull()) {
                mLauncher.toggleLauncher();
            }
        }
    }

    public class ToggleLauncher extends CommandBase
    {
        public ToggleLauncher() {
            addRequirements(mLauncher);
        }
        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mLauncher.toggleLauncher();
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // Returns true when the command should end.
        @Override
        public boolean isFinished()
        {
            return true;
        }

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {}
    }

    public class ShootFar extends CommandBase
    {
        public ShootFar() {
            addRequirements(mLauncher);
        }
        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mLauncher.setMotorSpeed(Flywheel.kFarRPS);
            mLauncher.setToggleTrue();
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // No isFinished because command is a WhileHeld

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {
            mLauncher.setMotorSpeed(mLauncher.getTargetRPS());
        }
    }
}
