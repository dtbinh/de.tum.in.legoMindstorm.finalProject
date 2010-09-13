package misc;
import lejos.nxt.Button;
import lejos.nxt.LCD;


public class Helper {
	private static final int columnLength = 16;
	private static final int numRows = 8;
	
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
		drawText(text);
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
}
