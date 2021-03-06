package miscBrick;
import Graph.Pair;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import misc.Direction;


/**
 * 
 * @author rohrmann
 *
 */
public class Helper {
	
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
		int charInLine = Config.columnLength - column;
		int charWritten = 0;
		while(charWritten < text.length() || row > Config.numRows){
			if(charWritten+charInLine > text.length()){
				LCD.drawString(text.substring(charWritten)+getWhitespaces(text.length()-charWritten,charInLine),column,row);
				charWritten = text.length();
			}
			else{
				LCD.drawString(text.substring(charWritten,charWritten+charInLine),column,row);
				charWritten += charInLine;
			}
			
			charInLine=Config.columnLength;
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
	
	public static Pair calcPos(Pair position, Direction dir){
		return calcPos(position,dir,1);
	}
	
	public static Pair calcPos(Pair position, Direction dir,int dist){
		switch(dir){
		case NORTH:
			return new Pair(position.getX(),position.getY()+dist);
		case SOUTH:
			return new Pair(position.getX(),position.getY()-dist);
		case WEST:
			return new Pair(position.getX()-dist,position.getY());
		default:
			return new Pair(position.getX()+dist,position.getY());
		}
	}
	
}
