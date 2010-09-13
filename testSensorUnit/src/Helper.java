import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;


public class Helper {
	private static final int columnLength = 16;
	private static final int numRows = 8;
	private static final int pollingIntervall = 25;
	
	public static void drawString(String text, int column, int row){
		LCD.drawString(text+getWhitespaces(text.length(),16),column,row);
	}
	
	public static String getWhitespaces(int textLength,int numChars){
		String result = "";
		
		for(int i =0; i< numChars-textLength;i++){
			result += " ";
		}
		
		return result;
	}
	
	public static void drawText(String text, int column, int row){
		LCD.clearDisplay();
		int charInLine = columnLength - column;
		int charWritten = 0;
		while(charWritten < text.length() || row > numRows){
			if(charWritten+charInLine > text.length()){
				LCD.drawString(text.substring(charWritten)+getWhitespaces(text.length()-charWritten,charInLine),column,row);
				charWritten = text.length();
			}
			else{
				LCD.drawString(text.substring(charWritten,charWritten+charInLine),column,row);
				charWritten += charInLine;
			}
			
			charInLine=columnLength;
			column = 0;
			row++;
		}
	}
	
	public static void drawText(String text){
		drawText(text,0,0);
	}
	
	public static void error(String text){
		drawText(text,0,0);
		Button.waitForPress();
		System.exit(1);
	}
	
	public static void drawColor(int[] color, int column,int row){
		String str = "("+color[0] +"," + color[1] + "," + color[2]+")";
		drawString(str,column,row);
	}
	
	public static void drawInt(int number,int column,int row){
		drawString(number+"",column,row);
	}
	
	public static ColorSettings initColors(String[] colors, int times, SensorPort port){
		ColorSensor sensor = new ColorSensor(port);
		ColorSettings result = new ColorSettings();
		
		drawText("Port:"+port.getId() + " white balancing Press Button");
		Button.waitForPress();
		
		sensor.initWhiteBalance();
		
		drawText("Port:"+port.getId() + " black level Press Button");
		Button.waitForPress();
		
		sensor.initBlackLevel();
		
		for(int i=0;i<colors.length;i++){
			drawText("Port:"+port.getId() + " color "+colors[i]+" Press Button");
			Button.waitForPress();
			drawText("Aim on color");
			float[] accColor= {0,0,0};
			for(int j = 0;j<times;j++){
				accColor[0] += sensor.getRedComponent();
				accColor[1] += sensor.getGreenComponent();
				accColor[2] += sensor.getBlueComponent();
				
				try{
					Thread.sleep(pollingIntervall);
				}catch(InterruptedException ex){
				}
			}
			
			accColor[0] /= times;
			accColor[1] /= times;
			accColor[2] /= times;
			
			int[] color = {Math.round(accColor[0]),Math.round(accColor[1]),Math.round(accColor[2])};
			
			result.addColor(colors[i], color);
		}
		
		return result;
	}
	
	public static LightSettings initLight(int tolerance,SensorPort lightPort){
		Helper.drawText("Port:" + lightPort.getId() + " init light sensor");
		LightSensor sensor = new LightSensor(lightPort);
		sensor.setFloodlight(false);
		
		boolean notPressed = true;
		boolean pressed = false;
		
		while(notPressed){
			if(Button.readButtons()!= 0)
				pressed = true;
			
			if(pressed && Button.readButtons()==0)
				notPressed=false;
			
			Helper.drawInt(sensor.getLightValue(), 0, 3);
		}
		
		return new LightSettings(sensor.readValue(),tolerance);
		
	}
}
