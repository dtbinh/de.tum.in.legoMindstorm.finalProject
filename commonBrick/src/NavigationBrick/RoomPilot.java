package NavigationBrick;

import Color.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Robot;
import miscBrick.RoomInformation;

/**
 * 
 * @author rohrmann
 *
 */
public class RoomPilot {
	
	private Arbitrator arbitrator;
	private Robot robot;
	
	
	public RoomPilot(Robot robot){
		this.robot = robot;
	}
	
	public Color goToNextRoom(){
		RoomInformation information= new RoomInformation();
		Behavior driveForward = new DriveForward(robot,information);
		Behavior followLine = new FollowLine(robot,information);
		Behavior checkRoom = new CheckRoom(robot,15,5,information);
		Behavior[] behaviors = {driveForward, followLine, checkRoom};
		arbitrator = new Arbitrator(behaviors,true);
		arbitrator.start();
		return information.getRoomColor();
	}
	
	public void rotate(float angle){
		robot.getPilot().rotate(angle);
	}
	
	public void rotate(float angle,boolean immediateReturn){
		robot.getPilot().rotate(angle, immediateReturn);
	}
	
	public boolean isMoving(){
		return robot.getPilot().isMoving();
	}
	
	public void stop(){
		robot.getPilot().stop();
	}
	
	public void reset(){
		robot.getPilot().reset();
	}
	
	public float getAngle(){
		return robot.getPilot().getAngle();
	}

}