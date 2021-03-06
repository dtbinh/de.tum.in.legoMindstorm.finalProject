import Color.Color;
import Color.ColorSettings;
import Light.LightSettings;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;

public class TestNavigator {
	
	public static void main(String[] args){
		TachoPilot pilot = new TachoPilot(5.6f,10.35f,Motor.A,Motor.B);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		int times = 5;
		int pollingInterval = 25;
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,times,pollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance =5;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		
		pilot.setMoveSpeed(5);
		pilot.setTurnSpeed(5);
		Behavior driveForward = new DriveForward(robot);
		Behavior followLine = new FollowLine(robot);
		Behavior checkCrossing = new CheckCrossing(robot,15,5);
		Behavior checkRoom = new CheckRoom(robot);
		Behavior[] behaviors = {driveForward, followLine, checkCrossing, checkRoom};
		Arbitrator arbitrator = new Arbitrator(behaviors);
		
		arbitrator.start();
	}

}
