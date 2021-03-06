package NavigationBrick;
import Color.Color;
import ErrorHandlingBrick.RoomMissed;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Config;
import miscBrick.Helper;
import miscBrick.Robot;
import miscBrick.RoomInformation;

/**
 * 
 * @author rohrmann
 *
 */
public class CheckRoom implements Behavior {
	
	private Robot robot;
	private boolean active;
	private boolean terminated;
	private float distanceUntilActivation;
	private float tolerance;
	private RoomInformation information;
	private boolean roomMissedActive;


	public CheckRoom(Robot robot,float distanceUntilActivation,float tolerance,
			RoomInformation information, boolean roomMissedActive){
		this.robot = robot;
		this.distanceUntilActivation = distanceUntilActivation;
		this.tolerance = tolerance;
		active = false;
		terminated = true;
		this.information = information;
		this.roomMissedActive = roomMissedActive;
	}

	//@Override
	public void action(){
		Sound.beep();
		float oldSpeed = robot.getPilot().getMoveSpeed();
		robot.getPilot().setMoveSpeed(Config.checkRoomSpeed);
		robot.getPilot().forward();
		long startTime;
		Color color=Color.UNKNOWN;
		Color lastColor=Color.UNKNOWN;
		long startColor=System.currentTimeMillis();
		
		while(active && robot.getPilot().getTravelDistance() < distanceUntilActivation+tolerance){
			startTime = System.currentTimeMillis();
			lastColor = color;
			color = robot.getColor().getColorName();
						
			if(color != lastColor){
				startColor = System.currentTimeMillis();
			}
			else if(color== lastColor && color.isRoomColor() && System.currentTimeMillis()-startColor > Config.acceptionPeriodForColor){
				active = false;
				information.setRoomColor(color);
			}
			
			try{
				Thread.sleep(Config.checkRoomPollingInterval - (System.currentTimeMillis()-startTime));
			}catch(InterruptedException ex){
			}
		}
		
		robot.getPilot().stop();
	
		// active is set false when the color of the room is set -> if still active the room was
		// not found
		if(active){
			Color missColor = null; 
				if(roomMissedActive){
					missColor = RoomMissed.action(robot, 6, information);
				}
			information.setRoomColor(missColor);
			}
		
		robot.getPilot().setMoveSpeed(oldSpeed);
		
		active = false;
		terminated = true;
	}

	//@Override
	public void suppress() {
		robot.getPilot().stop();
		active = false;
		while(!terminated){
			Thread.yield();
		}
		terminated = true;
	}

	//@Override
	public boolean takeControl() {
		
		if(!information.roomFound() && !active && terminated){
			if(robot.getPilot().getTravelDistance() >= distanceUntilActivation 
					&& robot.getPilot().getTravelDistance() <= distanceUntilActivation+tolerance){
				active = true;
				terminated = false;
				Helper.drawText("CheckRoom activated");
				return true;
			}
			return false;
		}
		else
			return false;
	}

}
